package com.barsite.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barsite.shared.di.ServiceLocator
import com.barsite.shared.domain.model.Produit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor() : ViewModel() {
    
    private val produitRepository = ServiceLocator.produitRepository
    
    private val _produits = MutableStateFlow<List<Produit>>(emptyList())
    val produits: StateFlow<List<Produit>> = _produits.asStateFlow()
    
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()
    
    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> = _total.asStateFlow()
    
    init {
        loadProduits()
    }
    
    private fun loadProduits() {
        viewModelScope.launch {
            produitRepository.getAll()
                .onSuccess { _produits.value = it }
        }
    }
    
    fun addToCart(produit: Produit, quantity: Double = 1.0) {
        val currentCart = _cartItems.value.toMutableList()
        val existingItem = currentCart.find { it.produit.id == produit.id }
        
        if (existingItem != null) {
            val index = currentCart.indexOf(existingItem)
            currentCart[index] = existingItem.copy(quantity = existingItem.quantity + quantity)
        } else {
            currentCart.add(CartItem(produit, quantity))
        }
        
        _cartItems.value = currentCart
        calculateTotal()
    }
    
    fun removeFromCart(produit: Produit) {
        _cartItems.value = _cartItems.value.filter { it.produit.id != produit.id }
        calculateTotal()
    }
    
    fun updateQuantity(produit: Produit, quantity: Double) {
        val currentCart = _cartItems.value.toMutableList()
        val itemIndex = currentCart.indexOfFirst { it.produit.id == produit.id }
        
        if (itemIndex != -1) {
            if (quantity > 0) {
                currentCart[itemIndex] = currentCart[itemIndex].copy(quantity = quantity)
            } else {
                currentCart.removeAt(itemIndex)
            }
            _cartItems.value = currentCart
            calculateTotal()
        }
    }
    
    private fun calculateTotal() {
        _total.value = _cartItems.value.sumOf { it.produit.prixVente * it.quantity }
    }
    
    fun clearCart() {
        _cartItems.value = emptyList()
        _total.value = 0.0
    }
}

data class CartItem(
    val produit: Produit,
    val quantity: Double
)
