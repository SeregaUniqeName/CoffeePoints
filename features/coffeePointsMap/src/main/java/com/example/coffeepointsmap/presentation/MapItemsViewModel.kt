package com.example.coffeepointsmap.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeepointsmap.models.MapEntity
import com.example.coffeepointsmap.useCases.GetALlPointsUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapItemsViewModel @Inject constructor(
    private val getALlPointsUseCase: GetALlPointsUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        _error.value = MapScreenErrors.Common(throwable.message.toString())
    }

    private val _pointsList: MutableStateFlow<List<MapEntity>> = MutableStateFlow(
        emptyList()
        )
    val pointsList get() = _pointsList.asStateFlow()

    private val _error: MutableStateFlow<MapScreenErrors> = MutableStateFlow(
        MapScreenErrors.Empty
    )
    val error get() = _error.asStateFlow()

    fun getAllItems() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                ensureActive()
                _pointsList.value = getALlPointsUseCase()
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