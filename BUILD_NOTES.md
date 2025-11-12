# Build Notes

## Network Requirements

This project requires access to the following repositories to build:
- `https://dl.google.com` - Android Gradle Plugin and AndroidX libraries
- `https://repo1.maven.org` - Maven Central
- `https://plugins.gradle.org` - Gradle plugins

If you encounter network issues during build, ensure:
1. You have internet connectivity
2. Your firewall allows access to these domains
3. If behind a corporate proxy, configure Gradle proxy settings in `gradle.properties`:

```properties
systemProp.http.proxyHost=your.proxy.host
systemProp.http.proxyPort=8080
systemProp.https.proxyHost=your.proxy.host
systemProp.https.proxyPort=8080
```

## Building the Project

### Prerequisites
- JDK 17 or higher
- Android SDK (for Android app)
- Xcode 15+ (for iOS app, macOS only)
- Internet connection

### First Build

The first build will download all dependencies, which may take several minutes:

```bash
# Build everything
./gradlew build

# Build specific modules
./gradlew :shared:build
./gradlew :androidApp:assembleDebug
```

### Common Build Issues

1. **"Could not resolve..." errors**
   - Check internet connection
   - Clear Gradle cache: `rm -rf ~/.gradle/caches`
   - Re-run build

2. **"SDK location not found"**
   - Create `local.properties` in project root
   - Add: `sdk.dir=/path/to/Android/Sdk`

3. **Kotlin version mismatch**
   - Ensure all Kotlin dependencies use version 2.0.21
   - Check `gradle/libs.versions.toml`

## Project Structure Validation

The project has been successfully created with the following structure:

✅ Root configuration (Gradle, settings, version catalog)
✅ Shared module (domain, data, use cases, repositories)
✅ Android app (Compose UI, ViewModels, Navigation)
✅ iOS app (SwiftUI views, ViewModels)
✅ SQLDelight schemas for offline storage
✅ Supabase client configuration
✅ Documentation (README, SETUP, ARCHITECTURE)

## Next Steps

1. Configure Supabase credentials in `SupabaseClient.kt`
2. Build the shared module first: `./gradlew :shared:build`
3. Build platform-specific apps
4. Run on emulator/simulator or physical device

## Environment Information

- Gradle: 8.5
- Kotlin: 2.0.21
- Android Gradle Plugin: 8.5.2
- Min Android SDK: 24
- Target Android SDK: 34
- Min iOS: 15.0
