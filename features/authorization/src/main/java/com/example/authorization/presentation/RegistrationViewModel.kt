package com.example.authorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization.FeatureTopLevelValues
import com.example.authorization.useCases.RegisterUserUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _screenState.value = RegistrationScreenState.RegistrationScreenContent(
            login = cachedLogin,
            password = "",
            passwordConfirm = "",
            error = RegistrationScreenState.RegistrationScreenContent.RegistrationErrors.ConnectionError(message = throwable.message.toString())
        )
    }

    private var cachedLogin: String = ""

    private val _screenState: MutableStateFlow<RegistrationScreenState> = MutableStateFlow(
        RegistrationScreenState.RegistrationScreenContent(
            login = "",
            password = "",
            passwordConfirm = ""
        )
    )
    val screenState get() = _screenState.asStateFlow()

    fun changeLogin(input: String) {
        _screenState.value =
            (_screenState.value as RegistrationScreenState.RegistrationScreenContent).copy(login = input)
    }

    fun changePassword(input: String) {
        _screenState.value =
            (_screenState.value as RegistrationScreenState.RegistrationScreenContent).copy(password = input)
    }

    fun changePasswordConfirm(input: String) {
        _screenState.value =
            (_screenState.value as RegistrationScreenState.RegistrationScreenContent).copy(passwordConfirm = input)
    }

    fun validateLogin(input: String): Boolean {
        return !FeatureTopLevelValues.emailRegex.matches(input)
    }

    fun validatePassword(input: String): Boolean {
        return !FeatureTopLevelValues.passwordRegex.matches(input)
    }

    fun validatePasswordConfirm(input: String): Boolean {
        return (_screenState.value as RegistrationScreenState.RegistrationScreenContent).password != input
    }

    fun register(login: String, password: String) {
        cachedLogin = login
        _screenState.value = RegistrationScreenState.Loading
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                ensureActive()
                registerUserUseCase(login, password)
                _screenState.value = RegistrationScreenState.RegistrationSuccess
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun clearInputs() {
        _screenState.value = (_screenState.value as RegistrationScreenState.RegistrationScreenContent).copy(
            password = "",
            passwordConfirm = ""
        )
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}