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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.divehub.android.R
import dev.divehub.android.ui.Spacing
import dev.divehub.android.ui.components.Radius
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import kotlinx.coroutines.launch

/** Why the live scanner could not open. The screen turns each into its own sentence (client contract, scanning). */
enum class CameraFailure { Missing, Blocked, Busy }

/** The focus frame's cap, so the guide stays a square on a tablet instead of swallowing the preview. */
private val ScannerFrameMaxWidth = 360.dp

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
 * tests, so the goldens review the guide frame, the hint and the way out without a real camera image.
 */
@Composable
internal fun CameraScannerFrame(
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    preview: @Composable () -> Unit,
) {
    Box(
        modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        preview()

        // The guide stays a square so a code fills it at any phone width; the border is the only chrome over the frame.
        Box(
            Modifier
                .align(Alignment.Center)
                .widthIn(max = ScannerFrameMaxWidth)
                .fillMaxWidth(0.8f)
                .aspectRatio(1f)
                .border(3.dp, Color.White.copy(alpha = 0.8f), RoundedCornerShape(Radius.control)),
        )

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(Spacing.s2)
                .size(48.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape),
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(R.string.scan_live_close),
                tint = Color.White,
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = Spacing.s4, vertical = Spacing.s5),
            color = Color.Black.copy(alpha = 0.55f),
            contentColor = Color.White,
            shape = RoundedCornerShape(Radius.control),
        ) {
            Text(
                text = stringResource(R.string.scan_live_hint),
                modifier = Modifier
                    .padding(horizontal = Spacing.s4, vertical = Spacing.s3)
                    .semantics { liveRegion = LiveRegionMode.Polite },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}
