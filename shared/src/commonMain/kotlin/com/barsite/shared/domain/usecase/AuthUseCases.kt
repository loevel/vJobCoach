package com.barsite.shared.domain.usecase

import com.barsite.shared.data.repository.AuthRepository
import com.barsite.shared.domain.model.Utilisateur

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Utilisateur> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password are required"))
        }
        return authRepository.signIn(email, password)
    }
}

class SignUpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
        nom: String,
        prenom: String
    ): Result<Utilisateur> {
        if (email.isBlank() || password.isBlank() || nom.isBlank() || prenom.isBlank()) {
            return Result.failure(IllegalArgumentException("All fields are required"))
        }
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("Password must be at least 6 characters"))
        }
        return authRepository.signUp(email, password, nom, prenom)
    }
}

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.signOut()
    }
}
