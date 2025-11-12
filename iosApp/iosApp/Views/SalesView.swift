import SwiftUI
import shared

struct SalesView: View {
    @StateObject private var viewModel = SalesViewModel()
    @Environment(\.presentationMode) var presentationMode
    @State private var showingProductPicker = false
    
    var body: some View {
        NavigationView {
            VStack {
                if viewModel.cartItems.isEmpty {
                    Spacer()
                    Text("Panier vide")
                        .font(.title2)
                        .foregroundColor(.gray)
                    Spacer()
                } else {
                    List {
                        ForEach(viewModel.cartItems, id: \.produit.id) { item in
                            CartItemRow(
                                item: item,
                                onIncrement: {
                                    viewModel.updateQuantity(produit: item.produit, quantity: item.quantity + 1)
                                },
                                onDecrement: {
                                    viewModel.updateQuantity(produit: item.produit, quantity: item.quantity - 1)
                                },
                                onRemove: {
                                    viewModel.removeFromCart(produit: item.produit)
                                }
                            )
                        }
                    }
                }
                
                // Bottom bar with total
                VStack(spacing: 12) {
                    HStack {
                        Text("Total")
                            .font(.title2)
                            .fontWeight(.bold)
                        Spacer()
                        Text("\(Int(viewModel.total)) FCFA")
                            .font(.title2)
                            .fontWeight(.bold)
                            .foregroundColor(.blue)
                    }
                    .padding(.horizontal)
                    
                    Button(action: {
                        viewModel.validateSale()
                        presentationMode.wrappedValue.dismiss()
                    }) {
                        Text("Valider la vente")
                            .fontWeight(.semibold)
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(viewModel.cartItems.isEmpty ? Color.gray : Color.blue)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                    }
                    .disabled(viewModel.cartItems.isEmpty)
                    .padding(.horizontal)
                }
                .padding(.vertical)
                .background(Color(.systemGray6))
            }
            .navigationTitle("Nouvelle vente")
            .navigationBarItems(
                leading: Button("Fermer") {
                    presentationMode.wrappedValue.dismiss()
                },
                trailing: Button(action: {
                    showingProductPicker = true
                }) {
                    Image(systemName: "plus.circle.fill")
                        .font(.title2)
                }
            )
            .sheet(isPresented: $showingProductPicker) {
                ProductPickerView(viewModel: viewModel)
            }
        }
    }
}

struct CartItemRow: View {
    let item: CartItem
    let onIncrement: () -> Void
    let onDecrement: () -> Void
    let onRemove: () -> Void
    
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(item.produit.nom)
                    .font(.headline)
                Text("\(Int(item.produit.prixVente)) FCFA")
                    .font(.subheadline)
                    .foregroundColor(.gray)
            }
            
            Spacer()
            
            HStack(spacing: 12) {
                Button(action: onDecrement) {
                    Image(systemName: "minus.circle")
                }
                
                Text("\(Int(item.quantity))")
                    .font(.headline)
                
                Button(action: onIncrement) {
                    Image(systemName: "plus.circle")
                }
                
                Button(action: onRemove) {
                    Image(systemName: "trash")
                        .foregroundColor(.red)
                }
            }
        }
    }
}

struct ProductPickerView: View {
    @ObservedObject var viewModel: SalesViewModel
    @Environment(\.presentationMode) var presentationMode
    
    var body: some View {
        NavigationView {
            List(viewModel.availableProducts, id: \.id) { produit in
                Button(action: {
                    viewModel.addToCart(produit: produit, quantity: 1.0)
                    presentationMode.wrappedValue.dismiss()
                }) {
                    HStack {
                        Text(produit.nom)
                        Spacer()
                        Text("\(Int(produit.prixVente)) FCFA")
                            .foregroundColor(.gray)
                    }
                }
            }
            .navigationTitle("Ajouter un produit")
            .navigationBarItems(trailing: Button("Annuler") {
                presentationMode.wrappedValue.dismiss()
            })
        }
    }
}

struct SalesView_Previews: PreviewProvider {
    static var previews: some View {
        SalesView()
    }
}
