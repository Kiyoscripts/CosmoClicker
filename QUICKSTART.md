# CosmoClicker - Quick Start Guide

## 🚀 Build APK (3 Steps)

### Step 1: Navigate to Project
```bash
cd /root/workspace/CosmoClicker
```

### Step 2: Build Debug APK
```bash
./gradlew assembleDebug
```

### Step 3: Find Your APK
```
Location: app/build/outputs/apk/debug/app-debug.apk
```

---

## 📱 Install on Device

### Option A: Via ADB (USB)
```bash
./gradlew installDebug
```

### Option B: Manual Install
1. Copy APK to your Android device
2. Open APK file on device
3. Install (allow unknown sources if prompted)

---

## 🎮 First Time Setup

### 1. Open CosmoClicker
- Launch the app from your app drawer

### 2. Grant Permissions
- **Overlay Permission**: Tap "Grant" → Select CosmoClicker → Enable
- **Accessibility**: Tap "Grant" → Find CosmoClicker → Enable

### 3. Configure Click Pattern
- Go to "Patterns" tab
- Select pattern type (e.g., Single Point)
- Set click interval (e.g., 1000ms)
- Set click count (0 = infinite)

### 4. Start Clicking!
- Go to "Home" tab
- Tap "Start" button
- The app will start auto-clicking

---

## 🎯 Pattern Types

### Single Point
- Clicks at one fixed location
- Best for: Simple repetitive tasks

### Multi Point
- Clicks at multiple saved positions sequentially
- Best for: Multi-step workflows

### Follow Touch
- Clicks where you touch
- Best for: Games requiring interaction

### Interval Burst
- Timed click sequences
- Best for: Precise timing needs

### Shizuku Mode
- Low-latency clicking via Shizuku
- Requires: Shizuku app installed
- Best for: Performance-critical tasks

---

## ⚙️ Customization

### Change App Name
Edit: `app/src/main/res/values/strings.xml`
```xml
<string name="app_name">YourAppName</string>
```

### Change Colors
Edit: `app/src/main/java/com/cosmoclicker/app/ui/theme/Color.kt`
```kotlin
val BluePrimary = Color(0xFF2196F3)
val YellowAccent = Color(0xFFFFC107)
```

### Change Package Name
1. Edit `app/build.gradle.kts`: `applicationId = "com.yourname.app"`
2. Rename package directories
3. Update imports in all Kotlin files

---

## 🐛 Troubleshooting

### Build Fails
```bash
# Clean and rebuild
./gradlew clean
./gradlew assembleDebug
```

### App Crashes on Start
- Check logcat for errors
- Ensure all permissions are granted
- Try reinstalling

### Clicking Not Working
1. Check Accessibility Service is enabled
2. Restart the app
3. Try Shizuku mode for better compatibility

### Shizuku Not Detected
1. Install Shizuku from Play Store
2. Enable wireless debugging (Android 11+)
3. Start Shizuku
4. Restart CosmoClicker

---

## 📚 Learn More

- Full documentation: `README.md`
- Project summary: `PROJECT_SUMMARY.md`
- Source code: `app/src/main/java/com/cosmoclicker/app/`

---

## 🎉 Happy Clicking!

Your CosmoClicker is ready to automate repetitive tasks!
