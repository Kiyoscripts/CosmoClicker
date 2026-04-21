#!/bin/bash

# CosmoClicker Build Script
# This script builds the CosmoClicker APK

set -e

echo "====================================="
echo "  CosmoClicker - Build Script"
echo "====================================="
echo ""

# Check if ANDROID_HOME is set
if [ -z "$ANDROID_HOME" ]; then
    echo "Warning: ANDROID_HOME is not set"
    echo "Please set ANDROID_HOME to your Android SDK location"
    echo ""
fi

# Check if gradle wrapper exists
if [ ! -f "./gradlew" ]; then
    echo "Error: gradlew not found!"
    echo "Make sure you're in the project root directory"
    exit 1
fi

# Build options
BUILD_TYPE=${1:-debug}

echo "Build type: $BUILD_TYPE"
echo ""

# Clean previous builds
echo "Cleaning previous builds..."
./gradlew clean --quiet

# Build APK
case $BUILD_TYPE in
    release)
        echo "Building release APK..."
        ./gradlew assembleRelease
        echo ""
        echo "====================================="
        echo "  Build Complete!"
        echo "====================================="
        echo ""
        echo "Release APK location:"
        echo "  app/build/outputs/apk/release/app-release.apk"
        echo ""
        ;;
    debug|*)
        echo "Building debug APK..."
        ./gradlew assembleDebug
        echo ""
        echo "====================================="
        echo "  Build Complete!"
        echo "====================================="
        echo ""
        echo "Debug APK location:"
        echo "  app/build/outputs/apk/debug/app-debug.apk"
        echo ""
        ;;
esac

# List built APKs
echo "Built APKs:"
find app/build/outputs/apk -name "*.apk" 2>/dev/null || echo "  No APKs found"
