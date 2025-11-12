package com.barsite.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Utilisateur(
    val id: String,
    val email: String,
    val nom: String,
    val prenom: String,
    val role: Role,
    val telephoneMobile: String? = null,
    val actif: Boolean = true,
    val createdAt: String,
    val updatedAt: String? = null
)

@Serializable
enum class Role {
    ADMIN,
    GERANT,
    VENDEUR,
    COMPTABLE
}
