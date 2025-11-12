# Project Setup Guide

This guide will walk you through setting up the Barsite Kotlin Multiplatform project from scratch.

## Development Environment Setup

### Prerequisites

1. **Java Development Kit (JDK)**
   - Install JDK 17 or higher
   - Set `JAVA_HOME` environment variable

2. **Android Studio** (for Android development)
   - Download from [developer.android.com](https://developer.android.com/studio)
   - Install Android SDK (API level 24-34)
   - Install Kotlin plugin (usually pre-installed)

3. **Xcode** (for iOS development - macOS only)
   - Install from Mac App Store
   - Install Xcode Command Line Tools: `xcode-select --install`
   - Accept license: `sudo xcodebuild -license accept`

4. **Git**
   - Install Git for version control

## Project Configuration

### 1. Gradle Configuration

The project uses Gradle 8.5. The wrapper is already included, so you don't need to install Gradle separately.

```bash
# Verify Gradle version
./gradlew --version
```

### 2. IDE Setup

#### Android Studio
1. Open Android Studio
2. Choose "Open an Existing Project"
3. Navigate to the project directory
4. Wait for Gradle sync to complete
5. Install any suggested plugins

#### IntelliJ IDEA (alternative)
1. Open IntelliJ IDEA
2. File > Open > Select project directory
3. Wait for indexing to complete

#### Xcode (iOS)
1. Open `iosApp/iosApp.xcodeproj` in Xcode
2. Select a simulator or device
3. Build and run

### 3. Supabase Setup

1. **Create Supabase Project**
   - Go to [supabase.com](https://supabase.com)
   - Click "New Project"
   - Fill in project details
   - Wait for project initialization

2. **Get Credentials**
   - Navigate to Project Settings > API
   - Copy `Project URL` and `anon public` key

3. **Update Application**
   - Copy `.env.example` to `.env`
   - Add your credentials:
     ```
     SUPABASE_URL=https://xxxxx.supabase.co
     SUPABASE_ANON_KEY=eyJhbGc...
     ```
   - Update `shared/src/commonMain/kotlin/com/barsite/shared/data/SupabaseClient.kt`

4. **Create Database Schema**
   - Go to Supabase SQL Editor
   - Run the SQL scripts from README.md
   - Verify tables are created

5. **Configure Row Level Security**
   - Enable RLS on all tables
   - Add appropriate policies for your use case

### 4. Build Verification

#### Build Shared Module
```bash
./gradlew :shared:build
```

#### Build Android App
```bash
./gradlew :androidApp:assembleDebug
```

#### Test Installation
```bash
# Run all tests
./gradlew test

# Run specific module tests
./gradlew :shared:test
```

## Common Setup Issues

### Gradle Issues

**Problem**: Gradle sync fails
**Solution**: 
```bash
# Clear Gradle cache
./gradlew clean
rm -rf ~/.gradle/caches

# Re-sync project
./gradlew build --refresh-dependencies
```

### Android Issues

**Problem**: SDK not found
**Solution**:
- Open Android Studio > SDK Manager
- Install required SDK versions (24-34)
- Update `local.properties` with SDK path

**Problem**: Build tools not found
**Solution**:
- Install Android Build Tools from SDK Manager
- Ensure version matches `build.gradle.kts`

### iOS Issues

**Problem**: Xcode can't find shared framework
**Solution**:
```bash
# Build shared framework first
./gradlew :shared:linkDebugFrameworkIosSimulatorArm64

# Then build in Xcode
```

**Problem**: Signing issues
**Solution**:
- Select your development team in Xcode
- Enable "Automatically manage signing"

### Supabase Issues

**Problem**: Connection timeout
**Solution**:
- Check internet connection
- Verify Supabase project is active
- Check API URL and key are correct

**Problem**: Authentication fails
**Solution**:
- Verify email confirmation is disabled in Supabase Auth settings (for development)
- Check user exists in Supabase dashboard
- Ensure proper RLS policies

## Next Steps

After setup:

1. **Explore the codebase**
   - Start with `shared/src/commonMain/kotlin/com/barsite/shared/domain/model/`
   - Review repository interfaces
   - Check ViewModels and UI screens

2. **Run the application**
   - Android: Use Android Studio or `./gradlew :androidApp:installDebug`
   - iOS: Build and run from Xcode

3. **Make your first change**
   - Add a new feature
   - Update UI
   - Create a pull request

4. **Read documentation**
   - Review ARCHITECTURE.md for design decisions
   - Check API_REFERENCE.md for available APIs

## Development Workflow

1. Create a feature branch
2. Make changes
3. Test locally
4. Commit with descriptive message
5. Push to remote
6. Create pull request
7. Wait for review and merge

## Useful Commands

```bash
# Clean build
./gradlew clean

# Build all modules
./gradlew build

# Run tests
./gradlew test

# Install Android app
./gradlew :androidApp:installDebug

# Check for dependency updates
./gradlew dependencyUpdates

# Format code (if ktlint is configured)
./gradlew ktlintFormat
```

## Getting Help

- Check the README.md
- Search existing issues on GitHub
- Ask in team communication channels
- Create a new issue if needed

---

Happy coding! 🚀
