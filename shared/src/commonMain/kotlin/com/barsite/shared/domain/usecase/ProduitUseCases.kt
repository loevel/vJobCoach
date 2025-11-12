package com.barsite.shared.domain.usecase

import com.barsite.shared.data.repository.ProduitRepository
import com.barsite.shared.domain.model.Produit

class GetAllProduitsUseCase(private val produitRepository: ProduitRepository) {
    suspend operator fun invoke(): Result<List<Produit>> {
        return produitRepository.getAll()
    }
}

class GetProduitByIdUseCase(private val produitRepository: ProduitRepository) {
    suspend operator fun invoke(id: String): Result<Produit> {
        return produitRepository.getById(id)
    }
}

class CreateProduitUseCase(private val produitRepository: ProduitRepository) {
    suspend operator fun invoke(produit: Produit): Result<Produit> {
        if (produit.nom.isBlank()) {
            return Result.failure(IllegalArgumentException("Product name is required"))
        }
        if (produit.prixVente <= 0 || produit.prixAchat <= 0) {
            return Result.failure(IllegalArgumentException("Prices must be greater than 0"))
        }
        return produitRepository.create(produit)
    }
}

class SearchProduitsUseCase(private val produitRepository: ProduitRepository) {
    suspend operator fun invoke(query: String): Result<List<Produit>> {
        return produitRepository.search(query)
    }
}
