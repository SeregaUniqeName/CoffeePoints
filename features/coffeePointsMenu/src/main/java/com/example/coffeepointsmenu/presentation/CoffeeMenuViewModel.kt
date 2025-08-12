package com.example.coffeepointsmenu.presentation

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeepointsmenu.models.CoffeeMenuItem
import com.example.coffeepointsmenu.useCases.AddPositionUseCase
import com.example.coffeepointsmenu.useCases.GetMenuItemsUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoffeeMenuViewModel @Inject constructor(
    private val getMenuItemsUseCase: GetMenuItemsUseCase,
    private val addPositionUseCase: AddPositionUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _screenState.value = if (_screenState.value is CoffeeMenuScreenState.Content) {
            (_screenState.value as CoffeeMenuScreenState.Content).copy(
                error = CoffeeMenuScreenState.Content.Error.Common(
                    message = throwable.message.toString()
                )
            )
        } else CoffeeMenuScreenState.Content(
            list = emptyList(),
            error = CoffeeMenuScreenState.Content.Error.Common(
                message = throwable.message.toString()
            )
        )
    }

    private val _screenState: MutableStateFlow<CoffeeMenuScreenState> =
        MutableStateFlow(CoffeeMenuScreenState.Loading)
    val screenState get() = _screenState.asStateFlow()

    fun getList(id: Int) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                ensureActive()
                val result = getMenuItemsUseCase.loadItems(id)
                _screenState.value = CoffeeMenuScreenState.Content(
                    list = result,
                    error = CoffeeMenuScreenState.Content.Error.Empty
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun increaseAmount(item: CoffeeMenuItem) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val newItem = item.copy(count = item.count + 1)
                val result = addPositionUseCase(newItem)
                _screenState.value = CoffeeMenuScreenState.Content(
                    list = result,
                    error = CoffeeMenuScreenState.Content.Error.Empty
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                throw e
            }
        }
    }

    fun decreaseAmount(item: CoffeeMenuItem) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val newItem = item.copy(count = item.count - 1)
                val result = addPositionUseCase(newItem)
                _screenState.value = CoffeeMenuScreenState.Content(
                    list = result,
                    error = CoffeeMenuScreenState.Content.Error.Empty
                )
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