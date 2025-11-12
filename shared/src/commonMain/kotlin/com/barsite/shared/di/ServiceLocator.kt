package com.barsite.shared.di

import com.barsite.shared.data.repository.*

object ServiceLocator {
    // Repositories
    private var _authRepository: AuthRepository? = null
    val authRepository: AuthRepository
        get() = _authRepository ?: AuthRepositoryImpl().also { _authRepository = it }
    
    private var _produitRepository: ProduitRepository? = null
    val produitRepository: ProduitRepository
        get() = _produitRepository ?: ProduitRepositoryImpl().also { _produitRepository = it }
    
    // Add other repositories as needed
    
    fun reset() {
        _authRepository = null
        _produitRepository = null
    }
}
