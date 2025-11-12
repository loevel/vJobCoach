package com.barsite.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Credit(
    val id: String,
    val clientId: String,
    val commandeId: String,
    val montant: Double,
    val montantPaye: Double = 0.0,
    val montantRestant: Double,
    val dateEcheance: String? = null,
    val statut: StatutCredit,
    val createdAt: String,
    val updatedAt: String? = null
)

@Serializable
data class PaiementCredit(
    val id: String,
    val creditId: String,
    val montant: Double,
    val modePaiement: ModePaiement,
    val utilisateurId: String,
    val notes: String? = null,
    val createdAt: String
)

@Serializable
enum class StatutCredit {
    IMPAYE,
    PARTIELLEMENT_PAYE,
    PAYE,
    EN_RETARD
}
