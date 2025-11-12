package com.barsite.shared.data.repository

import com.barsite.shared.domain.model.Commande
import com.barsite.shared.domain.model.StatutCommande

interface CommandeRepository {
    suspend fun getAll(): Result<List<Commande>>
    suspend fun getById(id: String): Result<Commande>
    suspend fun getByStatut(statut: StatutCommande): Result<List<Commande>>
    suspend fun create(commande: Commande): Result<Commande>
    suspend fun update(commande: Commande): Result<Commande>
    suspend fun delete(id: String): Result<Unit>
}
