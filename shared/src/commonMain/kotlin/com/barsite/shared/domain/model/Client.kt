package com.barsite.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Client(
    val id: String,
    val nom: String,
    val prenom: String? = null,
    val telephone: String,
    val email: String? = null,
    val adresse: String? = null,
    val ville: String? = null,
    val limiteCredit: Double? = null,
    val actif: Boolean = true,
    val createdAt: String,
    val updatedAt: String? = null
)
