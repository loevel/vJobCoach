package com.barsite.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Fournisseur(
    val id: String,
    val nom: String,
    val contact: String? = null,
    val telephone: String,
    val email: String? = null,
    val adresse: String? = null,
    val ville: String? = null,
    val pays: String = "Cameroun",
    val actif: Boolean = true,
    val createdAt: String,
    val updatedAt: String? = null
)

@Serializable
data class Approvisionnement(
    val id: String,
    val fournisseurId: String,
    val numeroFacture: String? = null,
    val montantTotal: Double,
    val items: List<ApprovisionnementItem> = emptyList(),
    val utilisateurId: String,
    val dateReception: String,
    val notes: String? = null,
    val createdAt: String,
    val updatedAt: String? = null
)

@Serializable
data class ApprovisionnementItem(
    val id: String,
    val approvisionnementId: String,
    val produitId: String,
    val quantite: Double,
    val prixUnitaire: Double,
    val montantTotal: Double
)
