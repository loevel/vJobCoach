package com.barsite.shared.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    val id: String,
    val produitId: String,
    val quantite: Double,
    val quantiteMin: Double,
    val quantiteMax: Double? = null,
    val emplacement: String? = null,
    val derniereEntree: String? = null,
    val derniereSortie: String? = null,
    val createdAt: String,
    val updatedAt: String? = null
)

@Serializable
data class MouvementStock(
    val id: String,
    val produitId: String,
    val type: TypeMouvement,
    val quantite: Double,
    val motif: String? = null,
    val utilisateurId: String,
    val createdAt: String
)

@Serializable
enum class TypeMouvement {
    ENTREE,
    SORTIE,
    AJUSTEMENT,
    INVENTAIRE
}
