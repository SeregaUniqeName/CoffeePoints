package com.example.coffeepoints.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authorization.presentation.RegistrationScreenState
import com.example.authorization.presentation.components.LogInTransition
import com.example.authorization.presentation.components.RegistrationPasswordConfirmTextField
import com.example.authorization.presentation.components.RegistrationPasswordTextField
import com.example.authorization.presentation.components.RegistrationTextField
import com.example.coffeepoints.getApplicationComponent
import com.example.coffeepoints.utils.ShowToast
import com.example.coffeepoints.R
import com.example.coffeepoints.ui.theme.CoffeePointsTheme

@Composable
fun RegistrationScreen(
    paddingValues: PaddingValues,
    onNextScreen: () -> Unit
) {

    val component = getApplicationComponent()
        .getAuthScreenComponentFactory().create()
    val viewModel: com.example.authorization.presentation.RegistrationViewModel = viewModel(factory = component.getViewModelsFactory())
    val screenState = viewModel.screenState.collectAsState()

    RegistrationContent(
        modifier = Modifier,
        screenState = screenState,
        onLoginChange = { viewModel.changeLogin(it) },
        onPasswordChange = { viewModel.changePassword(it) },
        onPasswordConfirmChange = { viewModel.changePasswordConfirm(it) },
        validateLogin = { viewModel.validateLogin(it) },
        validatePassword = { viewModel.validatePassword(it) },
        validatePasswordConfirm = { viewModel.validatePasswordConfirm(it) },
        onNextScreen = onNextScreen,
        registerUser = { login, password -> viewModel.register(login, password, onNextScreen) },
        onClearInputs = { viewModel.clearInputs() }
    )
}

@Composable
fun RegistrationContent(
    modifier: Modifier,
    screenState: State<RegistrationScreenState>,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    validateLogin: (String) -> Boolean,
    validatePassword: (String) -> Boolean,
    validatePasswordConfirm: (String) -> Boolean,
    onNextScreen: () -> Unit,
    registerUser: (login: String, password: String) -> Unit,
    onClearInputs: () -> Unit,
) {

    val currentState = screenState.value
    val loginError = rememberSaveable { mutableStateOf(false) }
    val passwordError = rememberSaveable { mutableStateOf(false) }
    val passwordConfirmError = rememberSaveable { mutableStateOf(false) }

    when (currentState) {
        RegistrationScreenState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is RegistrationScreenState.RegistrationScreenContent -> {
            if (currentState.error != RegistrationScreenState.RegistrationScreenContent.RegistrationErrors.Empty) {
                ShowToast(
                    message = (
                            currentState.error
                                    as RegistrationScreenState
                            .RegistrationScreenContent
                            .RegistrationErrors
                            .ConnectionError
                            ).message

                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                RegistrationTextField(
                    error = loginError,
                    modifier = modifier,
                    value = currentState.login,
                    onValueChange = onLoginChange,
                    validateInput = validateLogin
                )
                RegistrationPasswordTextField(
                    error = passwordError,
                    errorMessage = stringResource(R.string.password_input_error),
                    label = stringResource(R.string.password),
                    modifier = modifier,
                    value = currentState.password,
                    onValueChange = onPasswordChange,
                    validateInput = validatePassword
                )
                RegistrationPasswordConfirmTextField(
                    error = passwordConfirmError,
                    errorMessage = stringResource(R.string.password_matches_error),
                    label = stringResource(R.string.password_confirm),
                    modifier = modifier,
                    value = currentState.passwordConfirm,
                    onValueChange = onPasswordConfirmChange,
                    validateInput = validatePasswordConfirm
                )
                LogInTransition(
                    modifier = modifier,
                    onButtonClick = {
                        onClearInputs()
                        onNextScreen()
                    }
                )
                Button(
                    enabled = !loginError.value && !passwordError.value && !passwordConfirmError.value && currentState.login.isNotEmpty()
                            && currentState.password.isNotEmpty() && currentState.password == currentState.passwordConfirm,
                    modifier = modifier,
                    onClick = {
                        registerUser(
                            currentState.login,
                            currentState.password
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.registration))
                }

            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun RegScreenPreview() {
    CoffeePointsTheme {
        RegistrationContent(
            modifier = Modifier,
            screenState = mutableStateOf(
                RegistrationScreenState.RegistrationScreenContent(
                    login = "",
                    password = "",
                    passwordConfirm = "",
                    error = RegistrationScreenState.RegistrationScreenContent.RegistrationErrors.Empty,
                )
            ),
            onLoginChange = { it },
            onPasswordChange = { it },
            onPasswordConfirmChange = { it },
            validateLogin = { false },
            validatePassword = { false },
            validatePasswordConfirm = { false },
            onNextScreen = { },
            registerUser = { _, _ -> },
            onClearInputs = { }
        )
    }
}