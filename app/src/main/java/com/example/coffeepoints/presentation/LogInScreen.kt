package com.example.coffeepoints.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authorization.presentation.LogInScreenState
import com.example.authorization.presentation.LogInViewModel
import com.example.authorization.presentation.components.LogInPasswordTextField
import com.example.authorization.presentation.components.LogInTextField
import com.example.coffeepoints.ui.theme.CoffeePointsTheme
import com.example.coffeepoints.R
import com.example.coffeepoints.getApplicationComponent
import com.example.coffeepoints.utils.ShowToast

@Composable
fun LogInScreen(
    paddingValues: PaddingValues,
    onNextScreen: () -> Unit,
) {

    val component = getApplicationComponent()
        .getAuthScreenComponentFactory().create()
    val viewModel: LogInViewModel = viewModel(factory = component.getViewModelsFactory())
    val screenState = viewModel.screenState.collectAsState()

    LogInContent(
        modifier = Modifier,
        screenState = screenState,
        onLoginChange = { viewModel.changeLogin(it) },
        onPasswordChange = { viewModel.changePassword(it) },
        onNextScreen = onNextScreen,
        logInUser = { login, password -> viewModel.logIn(login, password) }
    )
}

@Composable
fun LogInContent(
    modifier: Modifier,
    screenState: State<LogInScreenState>,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNextScreen: () -> Unit,
    logInUser: (login: String, password: String) -> Unit,
) {

    val currentState = screenState.value

    when (currentState) {
        LogInScreenState.Loading -> {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is LogInScreenState.LogInScreenContent -> {
            if (currentState.error != LogInScreenState.LogInScreenContent.LogInErrors.Empty) {
                ShowToast(
                    message = (
                            currentState.error
                                    as LogInScreenState
                            .LogInScreenContent
                            .LogInErrors
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

                LogInTextField(
                    modifier = modifier,
                    value = currentState.login,
                    onValueChange = onLoginChange
                )
                LogInPasswordTextField(
                    label = stringResource(R.string.password),
                    modifier = modifier,
                    value = currentState.password,
                    onValueChange = onPasswordChange
                )
                Spacer(modifier = modifier.height(8.dp))
                Button(
                    enabled = currentState.login.isNotEmpty()
                            && currentState.password.isNotEmpty(),
                    modifier = modifier,
                    onClick = {
                        logInUser(
                            currentState.login,
                            currentState.password
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.log_in))
                }

            }
        }

        LogInScreenState.LogInSuccess -> {
            onNextScreen()
        }

    }
}

@Composable
@Preview(showSystemUi = true)
fun LogInScreenPreview() {
    CoffeePointsTheme {
        LogInContent(
            modifier = Modifier,
            screenState = mutableStateOf(
                LogInScreenState.LogInScreenContent(
                    login = "",
                    password = "",
                    error = LogInScreenState.LogInScreenContent.LogInErrors.Empty,
                )
            ),
            onLoginChange = { it },
            onPasswordChange = { it },
            onNextScreen = { },
            logInUser = { _, _ -> },
        )
    }
}