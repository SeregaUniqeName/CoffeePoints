package com.example.coffeepointslist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeepointslist.models.CoffeePointEntity
import com.example.coffeepointslist.useCases.GetAllPointsUseCase
import com.example.coffeepointslist.utils.calculateDistance
import com.example.data.TokenOutOfTimeException
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CoffeePointsViewModel @Inject constructor(
    private val getAllPointsUseCase: GetAllPointsUseCase,
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        _screenState.value = CoffeePointsScreenState.Error(
            throwable.message.toString()
        )
    }

    private val _screenState = MutableStateFlow<CoffeePointsScreenState>(
        CoffeePointsScreenState.Loading
    )
    val screenState get() = _screenState.asStateFlow()

    fun getPoints(lat: Double, lon: Double) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                ensureActive()
                if (_screenState.value is CoffeePointsScreenState.Loaded) {
                    calculateDistanceForList(
                        lat,
                        lon,
                        (_screenState.value as CoffeePointsScreenState.Loaded).coffeePointsList
                    )
                } else {
                    val result = getAllPointsUseCase()
                    calculateDistanceForList(lat, lon, result)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: TokenOutOfTimeException) {
                _screenState.value = CoffeePointsScreenState.TokenExpired
            } catch (e: Exception) {
                throw e
            }

        }
    }

    private fun calculateDistanceForList(lat: Double, lon: Double, list: List<CoffeePointEntity>) {
        val newList = mutableListOf<CoffeePointEntity>()
        list.forEach { item ->
            newList.add(
                item.copy(
                    distance = calculateDistance(lat, lon, item.lat, item.lon)
                )
            )
        }
        _screenState.value = CoffeePointsScreenState.Loaded(coffeePointsList = newList)

    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}