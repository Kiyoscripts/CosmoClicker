# CosmoClicker - Modern Android Auto Clicker

A modern, Material Design 3 autoclicker app for Android built with Jetpack Compose, Hilt, and Kotlin.

## Features

- **Multiple Click Patterns**
  - Single Point: Click at fixed coordinates
  - Multi Point: Sequential clicks at multiple positions
  - Follow Touch: Dynamic targeting
  - Interval Burst: Timed sequences
  - Shizuku Mode: Low-latency input (Android 11+)

- **Modern UI/UX**
  - Material Design 3 theming
  - Dynamic color (Material You) support for Android 12+
  - Light/Dark theme
  - Smooth animations and transitions

- **Advanced Features**
  - Configurable click interval (50ms - 10s)
  - Infinite or limited click count
  - Save/load presets
  - Floating overlay control
  - Foreground service for persistence

## Tech Stack

- **UI**: Jetpack Compose + Material Design 3
- **DI**: Hilt
- **Storage**: DataStore Preferences
- **Architecture**: MVVM with Repository pattern
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 34

## Build Instructions

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34

### Build Commands

```bash
# Navigate to project
cd CosmoClicker

# Build debug APK
./gradlew assembleDebug

# Build release APK (requires signing configuration)
./gradlew assembleRelease

# Install on connected device
./gradlew installDebug
```

### APK Location
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release.apk`

## Project Structure

```
app/src/main/java/com/cosmoclicker/app/
├── di/                 # Hilt dependency injection modules
├── data/
│   ├── model/          # Data classes (ClickPattern, Preset)
│   └── repository/     # SettingsRepository
├── service/            # AccessibilityService, ForegroundService
├── ui/
│   ├── theme/          # Material 3 theme, colors, typography
│   ├── navigation/     # App navigation
│   ├── screens/        # Compose screens (Home, Patterns, etc.)
│   └── components/     # Reusable components
└── util/               # Utilities (ShizukuHolder, PermissionUtils)
```

## Permissions Required

1. **Accessibility Service**: To perform automated clicks
2. **System Alert Window (Overlay)**: For floating control button
3. **Foreground Service**: To keep clicking active in background

## Shizuku Integration

CosmoClicker supports Shizuku for lower-latency clicking without the Accessibility Service warning overlay.

### Setup Shizuku:
1. Install Shizuku from Play Store or GitHub
2. Enable wireless debugging (Android 11+) or root
3. Start Shizuku
4. CosmoClicker will auto-detect and use Shizuku if available

## Customization

### Colors
Edit `app/src/main/java/com/cosmoclicker/app/ui/theme/Color.kt`:
```kotlin
val BluePrimary = Color(0xFF2196F3)
val YellowAccent = Color(0xFFFFC107)
```

### App Name
Change in `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">YourAppName</string>
```

## License

This project is open source and available under the MIT License.

## Credits

Built with:
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Material Design 3](https://m3.material.io/)
- [Hilt](https://dagger.dev/hilt/)
- [Shizuku](https://shizuku.rikka.app/)
