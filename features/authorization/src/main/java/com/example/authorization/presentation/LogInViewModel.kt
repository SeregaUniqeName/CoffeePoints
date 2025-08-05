package com.example.authorization.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization.useCases.LogInUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        rollbackState()
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

    fun logIn(login: String, password: String, navigateTo: () -> Unit) {
        cachedLogin = login
        _screenState.value = LogInScreenState.Loading
        viewModelScope.launch(exceptionHandler) {
            try {
                ensureActive()
                logInUseCase.invoke(login, password)
                navigateTo()
                delay(1000)
                rollbackState()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }

    private fun rollbackState() {
        _screenState.value = LogInScreenState.LogInScreenContent(
            login = cachedLogin,
            password = "",
        )
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}