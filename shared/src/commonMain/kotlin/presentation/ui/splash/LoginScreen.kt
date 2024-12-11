package presentation.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.component.DEFAULT__BUTTON_SIZE_EXTRA
import presentation.component.DefaultButton
import presentation.component.DefaultScreenUI
import presentation.component.PasswordTextField
import presentation.component.SimpleImageButton
import presentation.component.Spacer_16dp
import presentation.component.Spacer_32dp
import presentation.component.Spacer_4dp
import presentation.component.Spacer_8dp
import presentation.theme.DefaultTextFieldTheme
import presentation.ui.splash.view_model.LoginEvent
import presentation.ui.splash.view_model.LoginState
import shoping_by_kmp.shared.generated.resources.Res
import shoping_by_kmp.shared.generated.resources.apple
import shoping_by_kmp.shared.generated.resources.dont_have_an_account
import shoping_by_kmp.shared.generated.resources.email
import shoping_by_kmp.shared.generated.resources.enter_valid_email
import shoping_by_kmp.shared.generated.resources.enter_valid_password
import shoping_by_kmp.shared.generated.resources.facebook
import shoping_by_kmp.shared.generated.resources.forget_password
import shoping_by_kmp.shared.generated.resources.google
import shoping_by_kmp.shared.generated.resources.or_sign_in_with
import shoping_by_kmp.shared.generated.resources.password
import shoping_by_kmp.shared.generated.resources.sign_in
import shoping_by_kmp.shared.generated.resources.sign_up
import shoping_by_kmp.shared.generated.resources.welcome_title

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen(
    state: LoginState,
    events: (LoginEvent) -> Unit,
    navigateToMain: () -> Unit,
    navigateToRegister: () -> Unit
) {

    LaunchedEffect(state.navigateToMain) {
        if (state.navigateToMain) {
            navigateToMain()
        }
    }

    var isUsernameError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }

    DefaultScreenUI(
       queue = state.errorQueue,
        onRemoveHeadFromQueue = { events(LoginEvent.OnRemoveHeadFromQueue) },
        progressBarState = state.progressBarState
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(stringResource(Res.string.sign_in), style = MaterialTheme.typography.displaySmall)
            Spacer_16dp()
            Text(
                stringResource(Res.string.welcome_title),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer_32dp()

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(stringResource(Res.string.email))
                Spacer_4dp()
                TextField(
                    isError = isUsernameError,
                    value = state.usernameLogin,
                    onValueChange = {
                        if (it.length < 32) {
                            events(LoginEvent.OnUpdateUsernameLogin(it))
                            isUsernameError = it.isEmpty()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = DefaultTextFieldTheme(),
                    shape = MaterialTheme.shapes.small,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email,
                    ),
                )
                AnimatedVisibility(visible = isUsernameError) {
                    Text(
                        stringResource(Res.string.enter_valid_email),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Spacer_8dp()

                Text(stringResource(Res.string.password))
                Spacer_4dp()
                PasswordTextField(
                   // isError = isPasswordError,
                    value = state.passwordLogin,
                    onValueChange = {
                        events(LoginEvent.OnUpdatePasswordLogin(it))
                        isPasswordError = it.length < 8
                    },
                    modifier = Modifier.fillMaxWidth(),
                )

                AnimatedVisibility(visible = isPasswordError) {
                    Text(
                        stringResource(Res.string.enter_valid_password),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer_8dp()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    stringResource(Res.string.forget_password),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            }

            Spacer_32dp()


            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultButton(
                    progressBarState = state.progressBarState,
                    text = stringResource(Res.string.sign_in),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(DEFAULT__BUTTON_SIZE_EXTRA),
                    onClick = { events(LoginEvent.Login) }
                )

                Spacer(Modifier.height(32.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    HorizontalDivider(modifier = Modifier.width(75.dp))
                    Text(text = stringResource(Res.string.or_sign_in_with))
                    HorizontalDivider(modifier = Modifier.width(75.dp))
                }
                Spacer_32dp()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SimpleImageButton(Res.drawable.facebook)
                    SimpleImageButton(Res.drawable.apple)
                    SimpleImageButton(Res.drawable.google)
                }

            }

            Spacer_32dp()



            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(Res.string.dont_have_an_account),
                )
                Spacer_4dp()
                Text(
                    modifier = Modifier.clickable {
                        navigateToRegister()
                    },
                    text = stringResource(Res.string.sign_up),
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline
                )
            }

        }
    }
}
