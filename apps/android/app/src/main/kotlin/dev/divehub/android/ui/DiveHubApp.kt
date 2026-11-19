package dev.divehub.android.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.toggleableState
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.android.ui.dive.DiveDetailRoute
import dev.divehub.android.ui.dive.DiveDetailViewModel
import dev.divehub.android.ui.logbook.LogbookRoute
import dev.divehub.android.ui.logbook.LogbookViewModel
import dev.divehub.android.ui.tools.GasPlanViewModel
import dev.divehub.android.ui.tools.ToolsRoute
import dev.divehub.android.ui.tools.ToolsViewModel
import dev.divehub.shared.logbook.LogbookPaging
import dev.divehub.shared.ErrorCodes

@Composable
fun DiveHubApp(
    viewModel: MainViewModel,
    logbook: LogbookViewModel,
    dive: DiveDetailViewModel,
    tools: ToolsViewModel,
    gasPlan: GasPlanViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    // The open Dive is the app's only navigation state; the logbook keeps its filters underneath and back lands on
    // the page the Dive is on (ADR 0042).
    var openDiveId by rememberSaveable { mutableStateOf<String?>(null) }
    var showTools by rememberSaveable { mutableStateOf(false) }
    // A sign-out (or another User) drops the logbook's pages with the session (client contract §1 Security).
    LaunchedEffect(state.user?.id) {
        if (state.user == null) {
            logbook.forget()
            dive.forget()
            tools.forget()
            gasPlan.forget()
            openDiveId = null
            showTools = false
        } else {
            logbook.start(state.user!!.id)
            // After process death the id is back but the page was never read in this process.
            openDiveId?.let(dive::open)
        }
    }
    // ADR 0042: the logbook returns on the page the Dive is on, so stepping across a page boundary stays right.
    val closeDive = {
        val page = dive.state.value.neighbours?.position?.let(LogbookPaging::pageOf)
        if (page != null && page != logbook.state.value.page) logbook.showPage(page)
        openDiveId = null
    }
    BackHandler(enabled = openDiveId != null) { closeDive() }
    BackHandler(enabled = openDiveId == null && showTools) { showTools = false }
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when {
            state.loading -> Loading()
            state.user != null && openDiveId != null -> DiveDetailRoute(
                viewModel = dive,
                units = state.units,
                onBack = closeDive,
                onOpenDive = { id ->
                    dive.openNeighbour(id)
                    openDiveId = id
                },
            )
            state.user != null && showTools -> ToolsRoute(
                viewModel = tools,
                planViewModel = gasPlan,
                units = state.units,
                onBack = { showTools = false },
            )
            state.user != null -> LogbookRoute(
                viewModel = logbook,
                units = state.units,
                onSignOut = viewModel::signOut,
                onOpenTools = { showTools = true },
                onOpenDive = { id, filters ->
                    dive.open(id, filters)
                    openDiveId = id
                },
            )
            state.instanceUrl == null -> InstanceForm(state.error, viewModel::useInstance)
            else -> SignInForm(
                instanceUrl = state.instanceUrl!!,
                busy = state.busy,
                error = state.error,
                onChangeInstance = viewModel::changeInstance,
                onSignIn = viewModel::signIn,
            )
        }
    }
}

@Composable
private fun Loading() {
    val label = stringResource(R.string.loading)
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator(Modifier.semantics { contentDescription = label })
    }
}

@Composable
private fun InstanceForm(error: String?, onContinue: (String) -> Unit) {
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
        PrimaryButton(onClick = { onContinue(address) }, enabled = address.isNotBlank()) {
            Text(stringResource(R.string.instance_continue))
        }
    }
}

@Composable
private fun SignInForm(
    instanceUrl: String,
    busy: Boolean,
    error: String?,
    onChangeInstance: () -> Unit,
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
        Text(instanceUrl, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
