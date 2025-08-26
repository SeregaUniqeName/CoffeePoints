package com.example.confermscreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.confermscreen.models.OrderItemModel
import com.example.confermscreen.useCases.GetOrderUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ConfirmScreenViewModel @Inject constructor(
    private val getOrderUseCase: GetOrderUseCase,
) : ViewModel() {

    private val _list: MutableStateFlow<List<OrderItemModel>> = MutableStateFlow(
        emptyList()
    )
    val list get() = _list.asStateFlow()

    fun getOrder(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            val result = getOrderUseCase(id)
            _list.value = result
        }
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}