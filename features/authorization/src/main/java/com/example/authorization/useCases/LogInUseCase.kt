package com.example.authorization.useCases

import com.example.data.api.AuthorizationRepository
import jakarta.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: AuthorizationRepository,
){

    suspend operator fun invoke(login: String, password: String) {
        repository.authorize(login, password)
    }
}