package dev.divehub.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dev.divehub.android.AppContainer
import dev.divehub.android.BuildConfig
import dev.divehub.android.R
import dev.divehub.android.ui.dive.DiveDetailRoute
import dev.divehub.android.ui.dive.DiveDetailViewModel
import dev.divehub.android.ui.logbook.LogbookRoute
import dev.divehub.android.ui.logbook.LogbookViewModel
import dev.divehub.android.ui.navigation.AppNavHost
import dev.divehub.android.ui.navigation.AppScreens
import dev.divehub.android.ui.navigation.clearSavedTabStates
import dev.divehub.android.ui.navigation.sessionDestination
import dev.divehub.android.ui.settings.SettingsDebugRoute
import dev.divehub.android.ui.settings.SettingsRoute
import dev.divehub.android.ui.settings.SettingsViewModel
import dev.divehub.android.ui.tools.GasPlanViewModel
import dev.divehub.android.ui.tools.LeadEstimateViewModel
import dev.divehub.android.ui.tools.LoadingsViewModel
import dev.divehub.android.ui.tools.ToolPage
import dev.divehub.android.ui.tools.ToolsRoute
import dev.divehub.android.ui.tools.ToolsViewModel
import dev.divehub.shared.logbook.LogbookPaging
import dev.divehub.shared.ErrorCodes

@Composable
fun DiveHubApp(
    container: AppContainer,
    viewModel: MainViewModel,
    logbook: LogbookViewModel,
    dive: DiveDetailViewModel,
    tools: ToolsViewModel,
    gasPlan: GasPlanViewModel,
    lead: LeadEstimateViewModel,
    loadings: LoadingsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val screens = remember(container, viewModel, logbook, dive, tools, gasPlan, lead, loadings) {
        DiveHubScreens(container, viewModel, logbook, dive, tools, gasPlan, lead, loadings)
    }
    // A sign-out (or another User) drops the saved screens with the session (client contract §1 Security). Once the
    // restore has resolved (not while loading), it also drops Navigation's saved tab stacks.
    LaunchedEffect(state.user?.id, state.loading) {
        if (state.user == null) {
            logbook.forget()
            dive.forget()
            tools.forget()
            gasPlan.forget()
            lead.forget()
            loadings.forget()
            if (!state.loading) navController.clearSavedTabStates()
        }
    }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(Modifier.fillMaxSize()) {
            AppNavHost(
                navController = navController,
                screens = screens,
                destination = sessionDestination(state),
                divePage = { dive.state.value.neighbours?.position?.let(LogbookPaging::pageOf) },
                onLogbookPage = logbook::showPage,
            )
            when {
                state.loading -> Gate { LoadingScreen() }
                state.updateRequired -> Gate { UpdateRequiredScreen() }
            }
        }
    }
}

/** The screens bound to the shell; the navigation decisions live in [AppNavHost]. */
private class DiveHubScreens(
    private val container: AppContainer,
    private val viewModel: MainViewModel,
    private val logbook: LogbookViewModel,
    private val dive: DiveDetailViewModel,
    private val tools: ToolsViewModel,
    private val gasPlan: GasPlanViewModel,
    private val lead: LeadEstimateViewModel,
    private val loadings: LoadingsViewModel,
) : AppScreens {
    @Composable
    override fun instance() {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val error = (state.reachability as? Reachability.Unreachable)?.code
        InstanceForm(
            checking = state.reachability is Reachability.Checking,
            error = error,
            onContinue = viewModel::useInstance,
        )
    }

    @Composable
    override fun signIn() {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val instanceUrl = state.instanceUrl ?: return
        // The instance line is re-checked whenever this screen is shown, not only when the address was first entered.
        LaunchedEffect(instanceUrl) { viewModel.checkInstance() }
        SignInForm(
            instanceUrl = instanceUrl,
            busy = state.busy,
            reachability = state.reachability,
            error = state.error,
            onChangeInstance = viewModel::changeInstance,
            onCheckInstance = viewModel::checkInstance,
            onSignIn = viewModel::signIn,
        )
    }

    @Composable
    override fun logbook(page: Int, onPage: (Int) -> Unit, onOpenDive: (String) -> Unit) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        // The page is back from the route after process death; start the logbook there once the session is ready.
        LaunchedEffect(state.user?.id, page) {
            val userId = state.user?.id ?: return@LaunchedEffect
            logbook.start(userId, page)
        }
        LogbookRoute(
            viewModel = logbook,
            units = state.units,
            onOpenDive = { id, filters ->
                dive.open(id, filters)
                onOpenDive(id)
            },
            onPage = onPage,
        )
    }

    @Composable
    override fun settings(onOpenDebug: () -> Unit) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val settings: SettingsViewModel = viewModel(factory = SettingsViewModel.factory(container))
        SettingsRoute(
            viewModel = settings,
            email = state.user?.email,
            versionName = BuildConfig.VERSION_NAME,
            versionCode = BuildConfig.VERSION_CODE,
            onChangeInstance = viewModel::signOutAndChangeInstance,
            onSignOut = viewModel::signOut,
            onOpenDebug = onOpenDebug,
        )
    }

    @Composable
    override fun settingsDebug(owner: ViewModelStoreOwner, onBack: () -> Unit) {
        val settings: SettingsViewModel = viewModel(
            viewModelStoreOwner = owner,
            factory = SettingsViewModel.factory(container),
        )
        SettingsDebugRoute(viewModel = settings, onBack = onBack)
    }

    @Composable
    override fun dive(id: String, onBack: () -> Unit, onOpenDive: (String) -> Unit) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        // After process death the id is back from the route but the page was never read in this process. Wait for the
        // session: `open` reaches the API for the Provider names, and the instance URL is only set once restore is done.
        LaunchedEffect(id, state.user?.id) {
            if (state.user != null && dive.state.value.diveId != id) dive.open(id)
        }
        DiveDetailRoute(
            viewModel = dive,
            units = state.units,
            onBack = onBack,
            onOpenDive = { next ->
                dive.openNeighbour(next)
                onOpenDive(next)
            },
        )
    }

    @Composable
    override fun tools(page: ToolPage, onPage: (ToolPage) -> Unit, onBack: () -> Unit, onOpenDive: (String) -> Unit) {
        val state by viewModel.state.collectAsStateWithLifecycle()
        ToolsRoute(
            viewModel = tools,
            planViewModel = gasPlan,
            leadViewModel = lead,
            loadingsViewModel = loadings,
            units = state.units,
            page = page,
            onPage = onPage,
            onBack = onBack,
            onOpenDive = { id ->
                dive.open(id)
                onOpenDive(id)
            },
        )
    }
}

/** A full-screen gate above the graph: the app is loading, or the server refuses this release. */
@Composable
private fun Gate(content: @Composable () -> Unit) {
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        content()
    }
}

/**
 * The branded loading screen while session restore runs (FAH-315): the Waypoint tile, the wordmark and the slogan.
 * Every colour comes from the Material scheme, so the screen follows the system theme and the dark start gets the
 * reversed white mark on ink instead of a light flash.
 */
@Composable
internal fun LoadingScreen(modifier: Modifier = Modifier) {
    val scheme = MaterialTheme.colorScheme
    val label = stringResource(R.string.loading)
    val markColor = if (scheme.background.luminance() < 0.5f) scheme.onBackground else scheme.tertiary
    Column(
        modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_splash_mark),
            contentDescription = label,
            tint = markColor,
            modifier = Modifier.size(96.dp),
        )
        Spacer(Modifier.height(24.dp))
        Text(
            stringResource(R.string.splash_wordmark),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold,
            color = scheme.onBackground,
        )
        Spacer(Modifier.height(12.dp))
        Text(
            stringResource(R.string.loading_slogan),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = scheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun UpdateRequiredScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            stringResource(R.string.update_required_title),
            modifier = Modifier.semantics { heading() },
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(8.dp))
        Text(
            stringResource(R.string.update_required_body),
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
internal fun InstanceForm(checking: Boolean, error: String?, onContinue: (String) -> Unit) {
    var address by remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            stringResource(R.string.instance_title),
            modifier = Modifier.semantics { heading() },
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.instance_about), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.instance_label)) },
            placeholder = { Text(stringResource(R.string.instance_hint)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            isError = error != null,
        )
        ErrorText(error)
        Spacer(Modifier.height(16.dp))
        PrimaryButton(onClick = { onContinue(address) }, enabled = !checking && address.isNotBlank()) {
            if (checking) {
                Spinner()
                Spacer(Modifier.width(8.dp))
            }
            Text(stringResource(R.string.instance_continue))
        }
    }
}

@Composable
internal fun SignInForm(
    instanceUrl: String,
    busy: Boolean,
    reachability: Reachability,
    error: String?,
    onChangeInstance: () -> Unit,
    onCheckInstance: () -> Unit,
    onSignIn: (String, String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            stringResource(R.string.sign_in_title),
            modifier = Modifier.semantics { heading() },
            style = MaterialTheme.typography.headlineMedium,
        )
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                instanceUrl,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.width(8.dp))
            InstanceStatus(reachability, onCheckInstance)
        }
        TextButton(onClick = onChangeInstance) { Text(stringResource(R.string.instance_change)) }
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.sign_in_about), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentType = ContentType.Username },
            label = { Text(stringResource(R.string.form_email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = error != null,
        )
        Spacer(Modifier.height(8.dp))
        PasswordField(
            value = password,
            onValueChange = { password = it },
            visible = passwordVisible,
            onToggleVisibility = { passwordVisible = !passwordVisible },
            isError = error != null,
        )
        ErrorText(error)
        Spacer(Modifier.height(16.dp))
        PrimaryButton(
            onClick = {
                passwordVisible = false
                onSignIn(email, password)
            },
            enabled = !busy && email.isNotBlank() && password.isNotEmpty(),
        ) {
            if (busy) Spinner() else Text(stringResource(R.string.sign_in_submit))
        }
        Spacer(Modifier.height(24.dp))
        Text(
            stringResource(R.string.sign_in_no_account),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

/** The sign-in screen's instance line: connected, checking, or why the last check failed; tapping checks again. */
@Composable
private fun InstanceStatus(reachability: Reachability, onCheck: () -> Unit) {
    val label = when (reachability) {
        Reachability.Unknown, Reachability.Checking -> stringResource(R.string.instance_checking)
        Reachability.Reachable -> stringResource(R.string.instance_reachable)
        is Reachability.Unreachable -> stringResource(R.string.instance_unreachable)
    }
    AssistChip(
        onClick = onCheck,
        label = { Text(label) },
        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
    )
}

/** A password field with the eye button (client contract §5): its label names the field and says what the button does. */
@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    visible: Boolean,
    onToggleVisibility: () -> Unit,
    isError: Boolean,
) {
    val label = stringResource(R.string.form_password)
    val toggleLabel = stringResource(R.string.password_show, label)
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentType = ContentType.Password },
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        isError = isError,
        trailingIcon = {
            TextButton(
                onClick = onToggleVisibility,
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier.semantics {
                    contentDescription = toggleLabel
                    toggleableState = if (visible) ToggleableState.On else ToggleableState.Off
                },
            ) {
                Text(
                    if (visible) stringResource(R.string.password_hide_short) else stringResource(R.string.password_show_short),
                )
            }
        },
    )
}

@Composable
private fun Spinner() {
    val label = stringResource(R.string.loading)
    CircularProgressIndicator(
        modifier = Modifier
            .height(18.dp)
            .semantics { contentDescription = label },
    )
}

@Composable
private fun ErrorText(code: String?) {
    if (code == null) return
    Spacer(Modifier.height(8.dp))
    Text(
        stringResource(
            when (code) {
                ErrorCodes.INVALID_CREDENTIALS -> R.string.error_invalid_credentials
                ErrorCodes.TOO_MANY_ATTEMPTS -> R.string.error_too_many_attempts
                ErrorCodes.USER_DISABLED -> R.string.error_user_disabled
                ErrorCodes.SIGN_IN_REQUIRED -> R.string.error_sign_in_required
                ErrorCodes.INVALID_INSTANCE_URL -> R.string.error_invalid_instance_url
                ErrorCodes.NOT_DIVEHUB -> R.string.error_not_divehub
                ErrorCodes.MISSING_TOKEN -> R.string.error_missing_token
                ErrorCodes.SERVER_ERROR -> R.string.error_server
                MainViewModel.NO_ANSWER -> R.string.error_no_answer
                else -> R.string.error_unknown
            },
        ),
        modifier = Modifier.semantics { liveRegion = LiveRegionMode.Assertive },
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
    )
}

/** The one main action: an ink button in light mode, a light one in dark mode (ADR 0064). */
@Composable
private fun PrimaryButton(onClick: () -> Unit, enabled: Boolean = true, content: @Composable RowScope.() -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSurface,
            contentColor = MaterialTheme.colorScheme.surface,
        ),
        content = content,
    )
}
