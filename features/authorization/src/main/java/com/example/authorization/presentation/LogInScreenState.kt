package com.example.authorization.presentation

sealed class LogInScreenState {

    data object Loading : LogInScreenState()

    data class LogInScreenContent(
        val login: String,
        val password: String,
        val error: LogInErrors = LogInErrors.Empty,
    ) : LogInScreenState() {

        sealed interface LogInErrors {
            data class ConnectionError(val message: String) : LogInErrors
            data object Empty : LogInErrors
        }
    }

}