package com.barsite.shared.data.repository

import com.barsite.shared.domain.model.Stock
import com.barsite.shared.domain.model.MouvementStock

interface StockRepository {
    suspend fun getAll(): Result<List<Stock>>
    suspend fun getById(id: String): Result<Stock>
    suspend fun getByProduit(produitId: String): Result<Stock?>
    suspend fun update(stock: Stock): Result<Stock>
    suspend fun addMouvement(mouvement: MouvementStock): Result<MouvementStock>
    suspend fun getMouvementsByProduit(produitId: String): Result<List<MouvementStock>>
    suspend fun getLowStock(): Result<List<Stock>>
}
