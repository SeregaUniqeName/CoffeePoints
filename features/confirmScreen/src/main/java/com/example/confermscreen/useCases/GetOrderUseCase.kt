package com.example.confermscreen.useCases

import com.example.confermscreen.models.OrderItemModel
import com.example.confermscreen.utils.toEntity
import com.example.data.api.ConfirmOrderRepository
import jakarta.inject.Inject

class GetOrderUseCase @Inject constructor(
    private val repository: ConfirmOrderRepository,
) {

    suspend operator fun invoke(id: Int) : List<OrderItemModel> {
        return repository.getOrder(id).filter { it.count != 0 }.map { it.toEntity() }
    }
}