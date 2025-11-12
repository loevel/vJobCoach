package com.barsite.shared.data.repository

import com.barsite.shared.domain.model.Client

interface ClientRepository {
    suspend fun getAll(): Result<List<Client>>
    suspend fun getById(id: String): Result<Client>
    suspend fun create(client: Client): Result<Client>
    suspend fun update(client: Client): Result<Client>
    suspend fun delete(id: String): Result<Unit>
    suspend fun search(query: String): Result<List<Client>>
}
