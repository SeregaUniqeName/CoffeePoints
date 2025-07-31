package com.example.authorization.useCases

import com.example.data.api.AuthorizationRepository
import jakarta.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthorizationRepository,
) {

    suspend operator fun invoke(login: String, password: String) {
        repository.createUser(login, password)
    }
}