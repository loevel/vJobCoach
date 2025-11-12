package com.barsite.shared.data.repository

import com.barsite.shared.data.SupabaseClient
import com.barsite.shared.domain.model.Produit
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProduitRepositoryImpl : ProduitRepository {
    private val client = SupabaseClient.client
    
    override suspend fun getAll(): Result<List<Produit>> {
        return try {
            val produits = client.from("produits")
                .select()
                .decodeList<Produit>()
            Result.success(produits)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getById(id: String): Result<Produit> {
        return try {
            val produit = client.from("produits")
                .select {
                    filter { eq("id", id) }
                }
                .decodeSingle<Produit>()
            Result.success(produit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getByCategorie(categorie: String): Result<List<Produit>> {
        return try {
            val produits = client.from("produits")
                .select {
                    filter { eq("categorie", categorie) }
                }
                .decodeList<Produit>()
            Result.success(produits)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun create(produit: Produit): Result<Produit> {
        return try {
            val created = client.from("produits")
                .insert(produit)
                .decodeSingle<Produit>()
            Result.success(created)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun update(produit: Produit): Result<Produit> {
        return try {
            val updated = client.from("produits")
                .update(produit) {
                    filter { eq("id", produit.id) }
                }
                .decodeSingle<Produit>()
            Result.success(updated)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun delete(id: String): Result<Unit> {
        return try {
            client.from("produits")
                .delete {
                    filter { eq("id", id) }
                }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun search(query: String): Result<List<Produit>> {
        return try {
            val produits = client.from("produits")
                .select {
                    filter { ilike("nom", "%$query%") }
                }
                .decodeList<Produit>()
            Result.success(produits)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override fun observeAll(): Flow<List<Produit>> = flow {
        val produits = getAll().getOrNull() ?: emptyList()
        emit(produits)
    }
}
