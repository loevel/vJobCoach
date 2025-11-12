# Barsite - Bar/Snack Management System

A Kotlin Multiplatform (KMP) mobile application for managing bars and snack businesses in Cameroon. Built with modern technologies including Supabase backend, SQLDelight for local caching, and native UIs for both Android and iOS.

## 🚀 Features

### Core Functionality
- **Authentication**: Secure user login and registration with Supabase Auth
- **Point of Sale (POS)**: Fast and intuitive sales interface
- **Product Management**: Full CRUD operations for products
- **Client Management**: Track client information and credit
- **Stock Management**: Monitor inventory levels with low stock alerts
- **Supplier Management**: Manage supplier information and orders
- **Reports & Analytics**: Business insights and sales reports

### Technical Features
- **Offline-First**: SQLDelight local database for offline functionality
- **Real-time Updates**: Supabase Realtime for live data synchronization
- **Cross-Platform**: Shared business logic between Android and iOS
- **Modern UI**: Jetpack Compose for Android, SwiftUI for iOS
- **Type-Safe**: Kotlin's type system with serialization

## 📱 Project Structure

```
.
├── shared/                          # Shared Kotlin Multiplatform module
│   ├── src/
│   │   ├── commonMain/             # Platform-agnostic code
│   │   │   ├── kotlin/
│   │   │   │   └── com/barsite/shared/
│   │   │   │       ├── data/       # Data layer
│   │   │   │       │   ├── repository/  # Repository implementations
│   │   │   │       │   └── SupabaseClient.kt
│   │   │   │       ├── domain/     # Domain layer
│   │   │   │       │   ├── model/      # Data models
│   │   │   │       │   └── usecase/    # Business logic
│   │   │   │       ├── di/         # Dependency injection
│   │   │   │       └── utils/      # Utilities
│   │   │   └── sqldelight/         # SQLDelight schemas
│   │   ├── androidMain/            # Android-specific code
│   │   └── iosMain/                # iOS-specific code
│   └── build.gradle.kts
│
├── androidApp/                      # Android application
│   ├── src/main/
│   │   ├── kotlin/com/barsite/android/
│   │   │   ├── ui/
│   │   │   │   ├── screens/        # Compose screens
│   │   │   │   ├── navigation/     # Navigation
│   │   │   │   ├── theme/          # Material Design theme
│   │   │   │   └── viewmodel/      # ViewModels
│   │   │   ├── di/                 # Hilt modules
│   │   │   ├── MainActivity.kt
│   │   │   └── BarsiteApplication.kt
│   │   ├── res/                    # Android resources
│   │   └── AndroidManifest.xml
│   └── build.gradle.kts
│
├── iosApp/                          # iOS application
│   └── iosApp/
│       ├── Views/                   # SwiftUI views
│       ├── ViewModels/              # iOS ViewModels
│       ├── Resources/               # Assets and resources
│       ├── BarsiteApp.swift        # App entry point
│       ├── ContentView.swift       # Root view
│       └── Info.plist
│
├── gradle/                          # Gradle wrapper and configs
│   ├── libs.versions.toml          # Centralized dependencies
│   └── wrapper/
├── build.gradle.kts                # Root build file
├── settings.gradle.kts             # Project settings
├── gradle.properties               # Gradle properties
├── .env.example                    # Environment variables template
└── README.md                       # This file
```

## 🛠️ Technology Stack

### Shared (Kotlin Multiplatform)
- **Kotlin** 2.0.21
- **Supabase KMP Client** 3.0.2 - Auth, Postgrest, Storage, Realtime
- **Ktor Client** 3.0.1 - HTTP client
- **SQLDelight** 2.0.2 - Type-safe SQL database
- **Kotlinx Serialization** 1.7.3 - JSON serialization
- **Kotlinx Coroutines** 1.9.0 - Async programming
- **Kotlinx DateTime** 0.6.1 - Date/time handling

### Android
- **Jetpack Compose** - Modern declarative UI
- **Material Design 3** - Google's design system
- **Hilt** 2.52 - Dependency injection
- **Navigation Compose** - Navigation library
- **ViewModel & LiveData** - MVVM architecture

### iOS
- **SwiftUI** - Apple's declarative UI framework
- **Combine** - Reactive programming
- **MVVM Architecture** - Clean architecture pattern

## 📋 Prerequisites

- **JDK 17** or higher
- **Android Studio** Arctic Fox or newer (for Android development)
- **Xcode 15** or newer (for iOS development, macOS only)
- **Supabase Account** - Create one at [supabase.com](https://supabase.com)

## 🚦 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/loevel/vJobCoach.git
cd vJobCoach
```

### 2. Configure Supabase

1. Create a new project on [Supabase](https://supabase.com)
2. Copy `.env.example` to `.env`
3. Update the `.env` file with your Supabase credentials:

```env
SUPABASE_URL=https://your-project.supabase.co
SUPABASE_ANON_KEY=your-anon-key-here
```

4. Update `shared/src/commonMain/kotlin/com/barsite/shared/data/SupabaseClient.kt` with your credentials

### 3. Set Up Supabase Database

Create the following tables in your Supabase project:

```sql
-- Utilisateurs (Users)
CREATE TABLE utilisateurs (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  email TEXT UNIQUE NOT NULL,
  nom TEXT NOT NULL,
  prenom TEXT NOT NULL,
  role TEXT NOT NULL,
  telephone_mobile TEXT,
  actif BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP
);

-- Produits (Products)
CREATE TABLE produits (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nom TEXT NOT NULL,
  description TEXT,
  categorie TEXT NOT NULL,
  prix_vente DECIMAL NOT NULL,
  prix_achat DECIMAL NOT NULL,
  unite TEXT NOT NULL,
  code_barres TEXT,
  image_url TEXT,
  actif BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP
);

-- Clients
CREATE TABLE clients (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nom TEXT NOT NULL,
  prenom TEXT,
  telephone TEXT NOT NULL,
  email TEXT,
  adresse TEXT,
  ville TEXT,
  limite_credit DECIMAL,
  actif BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP
);

-- Stocks
CREATE TABLE stocks (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  produit_id UUID REFERENCES produits(id),
  quantite DECIMAL NOT NULL,
  quantite_min DECIMAL NOT NULL,
  quantite_max DECIMAL,
  emplacement TEXT,
  derniere_entree TIMESTAMP,
  derniere_sortie TIMESTAMP,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP
);

-- Fournisseurs (Suppliers)
CREATE TABLE fournisseurs (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  nom TEXT NOT NULL,
  contact TEXT,
  telephone TEXT NOT NULL,
  email TEXT,
  adresse TEXT,
  ville TEXT,
  pays TEXT DEFAULT 'Cameroun',
  actif BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP
);

-- Enable Row Level Security
ALTER TABLE utilisateurs ENABLE ROW LEVEL SECURITY;
ALTER TABLE produits ENABLE ROW LEVEL SECURITY;
ALTER TABLE clients ENABLE ROW LEVEL SECURITY;
ALTER TABLE stocks ENABLE ROW LEVEL SECURITY;
ALTER TABLE fournisseurs ENABLE ROW LEVEL SECURITY;

-- Add policies as needed for your security requirements
```

### 4. Build and Run

#### Android

```bash
# From project root
./gradlew :androidApp:assembleDebug

# Or open in Android Studio and run
```

#### iOS

```bash
# Generate Xcode project (if needed)
cd iosApp
open iosApp.xcodeproj

# Build and run from Xcode
```

#### Shared Module

```bash
# Build shared module
./gradlew :shared:build

# Run tests
./gradlew :shared:test
```

## 🏗️ Architecture

### Shared Module Architecture

The shared module follows **Clean Architecture** principles:

1. **Domain Layer** (`domain/`)
   - Models: Plain Kotlin data classes
   - Use Cases: Business logic implementation
   - Repository Interfaces: Contracts for data access

2. **Data Layer** (`data/`)
   - Repository Implementations: Supabase integration
   - Database: SQLDelight for local caching
   - Network: Ktor client configuration

3. **DI Layer** (`di/`)
   - ServiceLocator: Simple dependency injection

### Android App Architecture

- **MVVM Pattern** with Jetpack Compose
- **Unidirectional Data Flow** with StateFlow
- **Hilt** for dependency injection
- **Navigation Compose** for screen navigation

### iOS App Architecture

- **MVVM Pattern** with SwiftUI
- **Combine** for reactive programming
- **ObservableObject** for state management

## 📦 Key Dependencies

### Version Catalog (`gradle/libs.versions.toml`)

All dependencies are managed centrally in the version catalog for easy maintenance and updates.

## 🔒 Security Considerations

1. **Never commit** `.env` file with real credentials
2. **Enable Row Level Security** on all Supabase tables
3. **Implement proper authentication** checks
4. **Validate user input** on both client and server
5. **Use HTTPS** for all API communications

## 🧪 Testing

```bash
# Run shared module tests
./gradlew :shared:test

# Run Android tests
./gradlew :androidApp:testDebugUnitTest

# Run Android instrumentation tests
./gradlew :androidApp:connectedAndroidTest
```

## 📝 Development Guidelines

### Code Style
- Follow Kotlin official coding conventions
- Use meaningful variable and function names
- Write self-documenting code
- Add comments only when necessary

### Git Workflow
- Use feature branches
- Write descriptive commit messages
- Keep commits atomic and focused
- Review code before merging

### Database Migrations
- Always test migrations locally first
- Keep migration scripts versioned
- Document schema changes

## 🌍 Localization

The app supports French (primary language for Cameroon):
- Android: Resources in `res/values/strings.xml`
- iOS: SwiftUI Text with localized strings
- Add more languages by creating additional resource files

## 🐛 Troubleshooting

### Common Issues

1. **Gradle sync fails**
   - Ensure JDK 17 is installed and configured
   - Clear Gradle cache: `./gradlew clean`

2. **Supabase connection errors**
   - Verify credentials in SupabaseClient.kt
   - Check network connectivity
   - Ensure Supabase project is active

3. **iOS build fails**
   - Update CocoaPods: `pod install`
   - Clean build folder in Xcode
   - Check Xcode version compatibility

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Write/update tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **Development Team** - Initial work

## 🙏 Acknowledgments

- Supabase team for excellent KMP support
- JetBrains for Kotlin Multiplatform
- Cash App for SQLDelight
- The open-source community

## 📞 Support

For support, please:
1. Check the documentation
2. Search existing issues
3. Create a new issue if needed

---

**Made with ❤️ for Cameroon's business community**
