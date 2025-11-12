package com.barsite.shared.data.repository

import com.barsite.shared.data.SupabaseClient
import com.barsite.shared.domain.model.Utilisateur
import com.barsite.shared.domain.model.Role
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from

class AuthRepositoryImpl : AuthRepository {
    private val client = SupabaseClient.client
    
    override suspend fun signIn(email: String, password: String): Result<Utilisateur> {
        return try {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
            getCurrentUser()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun signUp(email: String, password: String, nom: String, prenom: String): Result<Utilisateur> {
        return try {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
            
            val userId = client.auth.currentUserOrNull()?.id ?: return Result.failure(Exception("User ID not found"))
            
            val utilisateur = Utilisateur(
                id = userId,
                email = email,
                nom = nom,
                prenom = prenom,
                role = Role.VENDEUR,
                createdAt = kotlinx.datetime.Clock.System.now().toString()
            )
            
            client.from("utilisateurs").insert(utilisateur)
            
            Result.success(utilisateur)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun signOut(): Result<Unit> {
        return try {
            client.auth.signOut()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getCurrentUser(): Result<Utilisateur?> {
        return try {
            val authUser = client.auth.currentUserOrNull()
            if (authUser == null) {
                Result.success(null)
            } else {
                val utilisateur = client.from("utilisateurs")
                    .select {
                        filter { eq("id", authUser.id) }
                    }
                    .decodeSingle<Utilisateur>()
                Result.success(utilisateur)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            client.auth.resetPasswordForEmail(email)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
