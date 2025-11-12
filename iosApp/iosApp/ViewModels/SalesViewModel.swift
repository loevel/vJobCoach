import Foundation
import Combine
import shared

class SalesViewModel: ObservableObject {
    @Published var cartItems: [CartItem] = []
    @Published var availableProducts: [Produit] = []
    @Published var total: Double = 0.0
    
    private let produitRepository: ProduitRepository
    private var cancellables = Set<AnyCancellable>()
    
    init() {
        self.produitRepository = ServiceLocator.shared.produitRepository
        loadProducts()
    }
    
    func loadProducts() {
        Task {
            do {
                let result = try await produitRepository.getAll()
                
                await MainActor.run {
                    if let products = result.getOrNull() {
                        self.availableProducts = products
                    }
                }
            } catch {
                print("Error loading products: \(error)")
            }
        }
    }
    
    func addToCart(produit: Produit, quantity: Double) {
        if let index = cartItems.firstIndex(where: { $0.produit.id == produit.id }) {
            cartItems[index].quantity += quantity
        } else {
            cartItems.append(CartItem(produit: produit, quantity: quantity))
        }
        calculateTotal()
    }
    
    func removeFromCart(produit: Produit) {
        cartItems.removeAll { $0.produit.id == produit.id }
        calculateTotal()
    }
    
    func updateQuantity(produit: Produit, quantity: Double) {
        if quantity <= 0 {
            removeFromCart(produit: produit)
        } else if let index = cartItems.firstIndex(where: { $0.produit.id == produit.id }) {
            cartItems[index].quantity = quantity
            calculateTotal()
        }
    }
    
    private func calculateTotal() {
        total = cartItems.reduce(0) { $0 + ($1.produit.prixVente * $1.quantity) }
    }
    
    func validateSale() {
        // TODO: Implement sale validation logic
        clearCart()
    }
    
    func clearCart() {
        cartItems.removeAll()
        total = 0.0
    }
}

struct CartItem: Identifiable {
    let id = UUID()
    let produit: Produit
    var quantity: Double
}
