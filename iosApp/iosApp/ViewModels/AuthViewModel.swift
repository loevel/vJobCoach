import Foundation
import Combine
import shared

class AuthViewModel: ObservableObject {
    @Published var isAuthenticated = false
    @Published var isLoading = false
    @Published var errorMessage: String?
    @Published var currentUser: Utilisateur?
    
    private let authRepository: AuthRepository
    private var cancellables = Set<AnyCancellable>()
    
    init() {
        self.authRepository = ServiceLocator.shared.authRepository
    }
    
    func login(email: String, password: String) {
        isLoading = true
        errorMessage = nil
        
        Task {
            do {
                let result = try await authRepository.signIn(email: email, password: password)
                
                await MainActor.run {
                    if let user = result.getOrNull() {
                        self.currentUser = user
                        self.isAuthenticated = true
                        self.isLoading = false
                    } else {
                        self.errorMessage = "Login failed"
                        self.isLoading = false
                    }
                }
            } catch {
                await MainActor.run {
                    self.errorMessage = error.localizedDescription
                    self.isLoading = false
                }
            }
        }
    }
    
    func signup(email: String, password: String, nom: String, prenom: String) {
        isLoading = true
        errorMessage = nil
        
        Task {
            do {
                let result = try await authRepository.signUp(
                    email: email,
                    password: password,
                    nom: nom,
                    prenom: prenom
                )
                
                await MainActor.run {
                    if let user = result.getOrNull() {
                        self.currentUser = user
                        self.isAuthenticated = true
                        self.isLoading = false
                    } else {
                        self.errorMessage = "Signup failed"
                        self.isLoading = false
                    }
                }
            } catch {
                await MainActor.run {
                    self.errorMessage = error.localizedDescription
                    self.isLoading = false
                }
            }
        }
    }
    
    func logout() {
        Task {
            do {
                _ = try await authRepository.signOut()
                await MainActor.run {
                    self.isAuthenticated = false
                    self.currentUser = nil
                }
            } catch {
                print("Logout error: \(error)")
            }
        }
    }
}
