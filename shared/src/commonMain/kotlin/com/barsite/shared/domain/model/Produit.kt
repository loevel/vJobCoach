package com.barsite.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Produit(
    val id: String,
    val nom: String,
    val description: String? = null,
    val categorie: String,
    val prixVente: Double,
    val prixAchat: Double,
    val unite: String,
    val codeBarres: String? = null,
    val imageUrl: String? = null,
    val actif: Boolean = true,
    val createdAt: String,
    val updatedAt: String? = null
)
