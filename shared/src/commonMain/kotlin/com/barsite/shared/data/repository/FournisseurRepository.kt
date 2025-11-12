package com.barsite.shared.data.repository

import com.barsite.shared.domain.model.Fournisseur

interface FournisseurRepository {
    suspend fun getAll(): Result<List<Fournisseur>>
    suspend fun getById(id: String): Result<Fournisseur>
    suspend fun create(fournisseur: Fournisseur): Result<Fournisseur>
    suspend fun update(fournisseur: Fournisseur): Result<Fournisseur>
    suspend fun delete(id: String): Result<Unit>
}
