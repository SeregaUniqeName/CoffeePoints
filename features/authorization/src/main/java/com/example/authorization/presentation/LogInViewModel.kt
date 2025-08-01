package com.example.authorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization.useCases.LogInUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _screenState.value = LogInScreenState.LogInScreenContent(
            login = cachedLogin,
            password = "",
            error = LogInScreenState.LogInScreenContent.LogInErrors.ConnectionError(message = throwable.message.toString())
        )
    }

    private var cachedLogin: String = ""

    private val _screenState: MutableStateFlow<LogInScreenState> = MutableStateFlow(
        LogInScreenState.LogInScreenContent(
            login = "",
            password = ""
        )
    )
    val screenState get() = _screenState.asStateFlow()

    fun changeLogin(input: String) {
        _screenState.value =
            (_screenState.value as LogInScreenState.LogInScreenContent).copy(login = input)
    }

    fun changePassword(input: String) {
        _screenState.value =
            (_screenState.value as LogInScreenState.LogInScreenContent).copy(password = input)
    }

    fun logIn(login: String, password: String) {
        _screenState.value = LogInScreenState.Loading
        cachedLogin = login
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                ensureActive()
                logInUseCase.invoke(login, password)
                _screenState.value = LogInScreenState.LogInSuccess
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}