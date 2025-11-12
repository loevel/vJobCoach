package com.barsite.shared.data.repository

import com.barsite.shared.domain.model.Utilisateur

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Utilisateur>
    suspend fun signUp(email: String, password: String, nom: String, prenom: String): Result<Utilisateur>
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUser(): Result<Utilisateur?>
    suspend fun resetPassword(email: String): Result<Unit>
}
