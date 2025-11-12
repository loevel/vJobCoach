package com.barsite.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Commande(
    val id: String,
    val numero: String,
    val clientId: String? = null,
    val utilisateurId: String,
    val montantTotal: Double,
    val montantPaye: Double = 0.0,
    val montantRestant: Double,
    val statut: StatutCommande,
    val modePaiement: ModePaiement? = null,
    val items: List<CommandeItem> = emptyList(),
    val notes: String? = null,
    val createdAt: String,
    val updatedAt: String? = null
)

@Serializable
data class CommandeItem(
    val id: String,
    val commandeId: String,
    val produitId: String,
    val produitNom: String,
    val quantite: Double,
    val prixUnitaire: Double,
    val montantTotal: Double,
    val remise: Double = 0.0
)

@Serializable
enum class StatutCommande {
    EN_COURS,
    VALIDEE,
    ANNULEE,
    LIVREE
}

@Serializable
enum class ModePaiement {
    ESPECES,
    MOBILE_MONEY,
    CARTE_BANCAIRE,
    CREDIT,
    MIXTE
}
