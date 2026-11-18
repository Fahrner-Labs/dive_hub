package dev.divehub.android.ui

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.divehub.android.R
import dev.divehub.api.models.UserView
import dev.divehub.shared.ErrorCodes

@Composable
fun DiveHubApp(viewModel: MainViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        when {
            state.loading -> Loading()
            state.user != null -> SignedIn(state.user!!, state.busy, viewModel::signOut)
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
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        CircularProgressIndicator()
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
        Text(stringResource(R.string.instance_title), style = MaterialTheme.typography.headlineMedium)
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
        Button(
            onClick = { onContinue(address) },
            enabled = address.isNotBlank(),
            modifier = Modifier.fillMaxWidth(),
        ) { Text(stringResource(R.string.instance_continue)) }
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
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.sign_in_title), style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(4.dp))
        Text(instanceUrl, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        TextButton(onClick = onChangeInstance) { Text(stringResource(R.string.instance_change)) }
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.sign_in_about), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.form_email)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = error != null,
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.form_password)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = error != null,
        )
        ErrorText(error)
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { onSignIn(email, password) },
            enabled = !busy && email.isNotBlank() && password.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (busy) CircularProgressIndicator(Modifier.height(18.dp)) else Text(stringResource(R.string.sign_in_submit))
        }
        Spacer(Modifier.height(24.dp))
        Text(
            stringResource(R.string.sign_in_no_account),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SignedIn(user: UserView, busy: Boolean, onSignOut: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.signed_in_title, user.name), style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(8.dp))
        Text(stringResource(R.string.signed_in_about), style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onSignOut, enabled = !busy, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.sign_out))
        }
    }
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
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyMedium,
    )
}
