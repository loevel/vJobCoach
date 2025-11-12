import SwiftUI

struct DashboardView: View {
    @EnvironmentObject var authViewModel: AuthViewModel
    @State private var showingSales = false
    
    let columns = [
        GridItem(.flexible()),
        GridItem(.flexible())
    ]
    
    var body: some View {
        NavigationView {
            ScrollView {
                LazyVGrid(columns: columns, spacing: 16) {
                    DashboardCard(title: "Ventes", icon: "cart.fill", color: .blue) {
                        showingSales = true
                    }
                    
                    DashboardCard(title: "Produits", icon: "list.bullet", color: .green) {
                        // Navigate to products
                    }
                    
                    DashboardCard(title: "Clients", icon: "person.fill", color: .orange) {
                        // Navigate to clients
                    }
                    
                    DashboardCard(title: "Stock", icon: "archivebox.fill", color: .purple) {
                        // Navigate to stock
                    }
                    
                    DashboardCard(title: "Rapports", icon: "chart.bar.fill", color: .red) {
                        // Navigate to reports
                    }
                }
                .padding()
            }
            .navigationTitle("Tableau de bord")
            .navigationBarItems(trailing: Button("Déconnexion") {
                authViewModel.logout()
            })
            .sheet(isPresented: $showingSales) {
                SalesView()
            }
        }
    }
}

struct DashboardCard: View {
    let title: String
    let icon: String
    let color: Color
    let action: () -> Void
    
    var body: some View {
        Button(action: action) {
            VStack(spacing: 12) {
                Image(systemName: icon)
                    .font(.system(size: 48))
                    .foregroundColor(color)
                
                Text(title)
                    .font(.headline)
                    .foregroundColor(.primary)
            }
            .frame(maxWidth: .infinity)
            .frame(height: 120)
            .background(Color(.systemGray6))
            .cornerRadius(12)
        }
    }
}

struct DashboardView_Previews: PreviewProvider {
    static var previews: some View {
        DashboardView()
            .environmentObject(AuthViewModel())
    }
}
