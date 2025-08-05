package com.example.authorization.presentation

sealed class RegistrationScreenState() {

    data object Loading : RegistrationScreenState()

    data class RegistrationScreenContent(
        val login: String,
        val password: String,
        val passwordConfirm: String,
        val error: RegistrationErrors = RegistrationErrors.Empty,
    ) : RegistrationScreenState() {

        sealed interface RegistrationErrors {
            data class ConnectionError(val message: String) : RegistrationErrors
            data object Empty : RegistrationErrors
        }

    }
}