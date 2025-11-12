# Architecture Overview

## Introduction

Barsite is built using **Kotlin Multiplatform (KMP)** to maximize code sharing between Android and iOS while maintaining native UI/UX on each platform.

## Architecture Principles

### Clean Architecture

The project follows Clean Architecture principles with clear separation of concerns:

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│  (Android Compose / iOS SwiftUI)       │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          ViewModel Layer                │
│   (Business logic presentation)         │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Domain Layer (Shared)          │
│  - Use Cases                            │
│  - Domain Models                        │
│  - Repository Interfaces                │
└─────────────────┬───────────────────────┘
                  │
┌─────────────────▼───────────────────────┐
│          Data Layer (Shared)            │
│  - Repository Implementations           │
│  - Data Sources (Supabase, SQLDelight) │
│  - DTOs & Mappers                       │
└─────────────────────────────────────────┘
```

## Module Structure

### Shared Module

The `shared` module contains all platform-agnostic business logic.

#### Package Organization

```
com.barsite.shared/
├── data/
│   ├── repository/          # Repository implementations
│   │   ├── AuthRepositoryImpl.kt
│   │   ├── ProduitRepositoryImpl.kt
│   │   └── ...
│   ├── SupabaseClient.kt   # Supabase configuration
│   └── DatabaseDriverFactory.kt
│
├── domain/
│   ├── model/              # Domain entities
│   │   ├── Utilisateur.kt
│   │   ├── Produit.kt
│   │   ├── Client.kt
│   │   └── ...
│   └── usecase/            # Business logic
│       ├── AuthUseCases.kt
│       └── ProduitUseCases.kt
│
├── di/
│   └── ServiceLocator.kt   # Dependency injection
│
└── utils/
    ├── Constants.kt
    └── Extensions.kt
```

#### Source Sets

- **commonMain**: Platform-agnostic code
- **androidMain**: Android-specific implementations
- **iosMain**: iOS-specific implementations

### Android App

The `androidApp` module implements the Android UI using Jetpack Compose.

#### MVVM Pattern

```
UI (Composable)
    ↓ (user action)
ViewModel
    ↓ (calls use case)
Use Case (Shared)
    ↓ (calls repository)
Repository (Shared)
    ↓ (network/database)
Data Source
```

#### Package Organization

```
com.barsite.android/
├── ui/
│   ├── screens/
│   │   ├── login/LoginScreen.kt
│   │   ├── dashboard/DashboardScreen.kt
│   │   └── sales/SalesScreen.kt
│   ├── navigation/
│   │   ├── Screen.kt
│   │   └── BarsiteNavHost.kt
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Type.kt
│   │   └── Theme.kt
│   └── viewmodel/
│       ├── LoginViewModel.kt
│       └── SalesViewModel.kt
├── di/
│   └── AppModule.kt
├── MainActivity.kt
└── BarsiteApplication.kt
```

### iOS App

The `iosApp` implements the iOS UI using SwiftUI.

#### Structure

```
iosApp/
├── Views/
│   ├── LoginView.swift
│   ├── SignupView.swift
│   ├── DashboardView.swift
│   └── SalesView.swift
├── ViewModels/
│   ├── AuthViewModel.swift
│   └── SalesViewModel.swift
├── BarsiteApp.swift
├── ContentView.swift
└── Info.plist
```

## Data Flow

### Read Operation

```
User Action → ViewModel → Use Case → Repository → Supabase
                ↓                                      ↓
            Update UI ← State Flow ← Result ← Response
```

### Write Operation

```
User Input → ViewModel → Use Case → Repository → Supabase
                                         ↓            ↓
                                    SQLDelight ← Success
                                         ↓
                                   Update Cache
```

## Key Design Decisions

### 1. Repository Pattern

**Why**: Abstracts data sources, making the code testable and maintainable.

**Implementation**:
- Interface in domain layer
- Implementation in data layer
- Multiple data sources (Supabase + SQLDelight)

### 2. Use Cases

**Why**: Encapsulates business logic, making it reusable and testable.

**Implementation**:
- One class per operation
- Takes repository as dependency
- Returns Result type

### 3. ServiceLocator vs DI Framework

**Why**: Keeps shared module simple without platform-specific DI frameworks.

**Implementation**:
- ServiceLocator in shared module
- Hilt in Android app
- Manual injection in iOS app

### 4. SQLDelight for Local Storage

**Why**: Type-safe SQL queries, multiplatform support, compile-time verification.

**Implementation**:
- SQL files in `commonMain/sqldelight`
- Platform-specific drivers
- Repository pattern for access

### 5. Result Type for Error Handling

**Why**: Explicit error handling, no exceptions in business logic.

**Implementation**:
```kotlin
suspend fun getData(): Result<Data>
```

## State Management

### Android

Uses StateFlow for reactive state management:

```kotlin
class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Initial)
    val state: StateFlow<UiState> = _state.asStateFlow()
    
    fun loadData() {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repository.getData()
                .onSuccess { _state.value = UiState.Success(it) }
                .onFailure { _state.value = UiState.Error(it.message) }
        }
    }
}
```

### iOS

Uses Combine and @Published properties:

```swift
class MyViewModel: ObservableObject {
    @Published var data: [Item] = []
    @Published var isLoading = false
    
    func loadData() {
        isLoading = true
        Task {
            let result = try await repository.getData()
            await MainActor.run {
                self.data = result.getOrNull() ?? []
                self.isLoading = false
            }
        }
    }
}
```

## Testing Strategy

### Unit Tests (Shared Module)

- Test use cases with mock repositories
- Test repository logic with mock data sources
- Test domain model validation

### Integration Tests (Shared Module)

- Test repository with real Supabase (test project)
- Test SQLDelight operations

### UI Tests

- Android: Compose test framework
- iOS: SwiftUI PreviewProvider and XCTest

## Performance Considerations

### 1. Lazy Loading

- Load data on demand
- Implement pagination for large lists
- Use Flow for reactive updates

### 2. Caching Strategy

- Cache frequently accessed data in SQLDelight
- Implement refresh strategies
- Handle offline scenarios

### 3. Network Optimization

- Batch requests when possible
- Use Supabase Realtime for live updates
- Implement request debouncing

## Security Considerations

### 1. Authentication

- Store tokens securely (Keychain/Keystore)
- Implement token refresh
- Handle session expiration

### 2. Data Validation

- Validate on client and server
- Sanitize user input
- Use Kotlin's type system

### 3. Row Level Security

- Enable RLS on all tables
- Define proper policies
- Test security rules

## Future Improvements

1. **Offline Sync**
   - Implement sync queue
   - Handle conflict resolution
   - Background sync

2. **Analytics**
   - Add analytics tracking
   - Monitor app performance
   - Track user behavior

3. **Testing**
   - Increase test coverage
   - Add UI tests
   - Implement screenshot tests

4. **CI/CD**
   - Automate builds
   - Run tests on PR
   - Deploy to app stores

---

This architecture provides a solid foundation for building a scalable, maintainable, and testable multiplatform application.
