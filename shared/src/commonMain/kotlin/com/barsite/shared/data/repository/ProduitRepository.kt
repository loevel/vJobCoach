package com.barsite.shared.data.repository

import com.barsite.shared.domain.model.Produit
import kotlinx.coroutines.flow.Flow

interface ProduitRepository {
    suspend fun getAll(): Result<List<Produit>>
    suspend fun getById(id: String): Result<Produit>
    suspend fun getByCategorie(categorie: String): Result<List<Produit>>
    suspend fun create(produit: Produit): Result<Produit>
    suspend fun update(produit: Produit): Result<Produit>
    suspend fun delete(id: String): Result<Unit>
    suspend fun search(query: String): Result<List<Produit>>
    fun observeAll(): Flow<List<Produit>>
}
