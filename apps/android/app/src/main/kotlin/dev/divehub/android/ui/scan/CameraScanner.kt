package dev.divehub.android.ui.scan

import androidx.camera.core.CameraInfoUnavailableException
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraUnavailableException
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.InitializationException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.divehub.android.R
import dev.divehub.android.ui.CameraChrome
import dev.divehub.android.ui.DiveHubText
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.components.Radius
import dev.divehub.android.ui.findActivity
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import kotlinx.coroutines.launch

/** Why the live scanner could not open. The screen turns each into its own sentence (client contract, scanning). */
enum class CameraFailure { Missing, Blocked, Busy }

/** A contained target beside the close glyph; the guide's white stroke and the dark under-stroke that keeps it visible. */
private val CloseTarget = 48.dp
private val GuideStroke = 3.dp
private val GuideUnderStroke = 7.dp

/** The guide's cap, so it stays a square rather than swallowing a tablet's preview. */
private val GuideMaxSide = 360.dp

/**
 * The live scanner: CameraX's preview with frames analysed on the device and the first code's text handed on
 * unchanged. Reading a code stops analysis at once and closes the scanner; the analyser and the bound use cases are
 * torn down with the composition, so no camera work outlives the preview (client contract, scanning; ADR 0043).
 */
@Composable
fun CameraScanner(
    onCode: (String) -> Unit,
    onFailure: (CameraFailure) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val mainExecutor = remember(context) { ContextCompat.getMainExecutor(context) }
    val scope = rememberCoroutineScope()
    val previewView = remember(context) {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    // The preview is dark and full-bleed; keep the system bars readable over it, then restore the theme's choice.
    val view = LocalView.current
    val activity = context.findActivity()
    DisposableEffect(activity, view) {
        val controller = activity?.window?.let { WindowInsetsControllerCompat(it, view) }
        val lightStatusBars = controller?.isAppearanceLightStatusBars
        val lightNavigationBars = controller?.isAppearanceLightNavigationBars
        if (controller != null) {
            controller.isAppearanceLightStatusBars = false
            controller.isAppearanceLightNavigationBars = false
        }
        onDispose {
            if (controller != null) {
                if (lightStatusBars != null) controller.isAppearanceLightStatusBars = lightStatusBars
                if (lightNavigationBars != null) controller.isAppearanceLightNavigationBars = lightNavigationBars
            }
        }
    }

    DisposableEffect(previewView, lifecycleOwner) {
        val providerFuture = ProcessCameraProvider.getInstance(context)
        val analysisExecutor = Executors.newSingleThreadExecutor()
        val gate = QrFrameGate()
        var provider: ProcessCameraProvider? = null
        var analysis: ImageAnalysis? = null
        var disposed = false

        providerFuture.addListener({
            // The provider future can land after the preview closed; binding then would keep the camera open unseen.
            if (disposed) return@addListener
            try {
                val cameraProvider = providerFuture.get()
                provider = cameraProvider

                val preview = Preview.Builder().build().apply {
                    surfaceProvider = previewView.surfaceProvider
                }
                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                analysis = imageAnalysis
                imageAnalysis.setAnalyzer(analysisExecutor) { image ->
                    // Decode off the main thread, throttle to the gate's interval, and close every frame.
                    val text = try {
                        if (gate.due()) runCatching { QrImageDecoder.decode(image.toBitmap()) }.getOrNull() else null
                    } finally {
                        image.close()
                    }
                    val code = gate.firstText(text)
                    if (code != null) {
                        // Stop the pipeline here, on the analysis thread, before the recomposition unbinds it.
                        imageAnalysis.clearAnalyzer()
                        scope.launch { onCode(code) }
                    }
                }

                val selector = if (cameraProvider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)) {
                    CameraSelector.DEFAULT_BACK_CAMERA
                } else {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                }
                cameraProvider.bindToLifecycle(lifecycleOwner, selector, preview, imageAnalysis)
            } catch (error: Exception) {
                runCatching { analysis?.clearAnalyzer() }
                runCatching { provider?.unbindAll() }
                val failure = cameraFailure(error)
                scope.launch { onFailure(failure) }
            }
        }, mainExecutor)

        onDispose {
            disposed = true
            runCatching { analysis?.clearAnalyzer() }
            runCatching { provider?.unbindAll() }
            analysisExecutor.shutdownNow()
        }
    }

    CameraScannerFrame(
        onClose = onClose,
        modifier = modifier,
        preview = {
            AndroidView(
                factory = { previewView },
                modifier = Modifier.fillMaxSize(),
            )
        },
    )
}

/**
 * Which sentence a CameraX failure earns. A permission revoked while the preview is open is [CameraFailure.Blocked],
 * not "busy"; a camera that cannot be opened or resolved is missing; anything else is held elsewhere.
 */
internal fun cameraFailure(error: Throwable): CameraFailure = when (val cause = (error as? ExecutionException)?.cause ?: error) {
    is SecurityException -> CameraFailure.Blocked
    is CameraInfoUnavailableException,
    is InitializationException,
    is IllegalArgumentException,
    -> CameraFailure.Missing
    is CameraUnavailableException -> CameraFailure.Busy
    else -> CameraFailure.Busy
}

/**
 * The live scanner's frame and controls. [preview] is the camera surface on a device and a stand-in in the screenshot
 * tests, so the goldens review the guide frame, the hint and the way out without a real camera image. The guide is
 * composed between the close row and the hint, so a short screen pushes them apart rather than letting them collide.
 */
@Composable
internal fun CameraScannerFrame(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
) {
    val closeFocus = remember { FocusRequester() }
    LaunchedEffect(Unit) { closeFocus.requestFocus() }

    Box(
        modifier
            .fillMaxSize()
            .background(CameraChrome.Scene),
    ) {
        preview()

        Column(
            Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.s4, vertical = Spacing.s2),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .focusRequester(closeFocus)
                        .size(CloseTarget)
                        .background(CameraChrome.CloseScrim, CircleShape),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.scan_live_close),
                        tint = CameraChrome.Ink,
                    )
                }
            }

            // The guide takes the space the close row and the hint leave, so it never sits under the hint.
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = Spacing.s4),
                contentAlignment = Alignment.Center,
            ) {
                BoxWithConstraints {
                    val side = minOf(maxWidth * 0.8f, maxHeight * 0.8f, GuideMaxSide)
                    Box(
                        Modifier
                            .size(side)
                            .drawBehind {
                                val corner = CornerRadius(Radius.control.toPx())
                                drawRoundRect(
                                    color = CameraChrome.GuideUnder,
                                    cornerRadius = corner,
                                    style = Stroke(GuideUnderStroke.toPx()),
                                )
                                drawRoundRect(
                                    color = CameraChrome.Guide,
                                    cornerRadius = corner,
                                    style = Stroke(GuideStroke.toPx()),
                                )
                            },
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = Spacing.s4, vertical = Spacing.s5),
                color = CameraChrome.HintScrim,
                contentColor = CameraChrome.Ink,
                shape = RoundedCornerShape(Radius.control),
            ) {
                Text(
                    text = stringResource(R.string.scan_live_hint),
                    modifier = Modifier
                        .padding(horizontal = Spacing.s4, vertical = Spacing.s3)
                        .semantics { liveRegion = LiveRegionMode.Polite },
                    style = DiveHubText.body,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
