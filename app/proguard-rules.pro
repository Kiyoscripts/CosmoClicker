# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /sdk/tools/proguard/proguard-android.txt

# Hilt specific rules
-keep class dagger.internal.DaggerModule { *; }
-keep class * extends dagger.internal.DaggerModule { *; }
-keep class javax.inject.* { *; }
-keep class dagger.hilt.* { *; }
-keep class * extends dagger.hilt.* { *; }
-keep class dagger.hilt.android.internal.managers.* { *; }
-keep class dagger.hilt.android.internal.lifecycle.* { *; }
-keep class dagger.hilt.android.internal.managers.ComponentSupplier { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.DefaultLifecycleObserver { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }

# Shizuku specific rules
-keep class dev.rikka.shizuku.** { *; }
-keep class rikka.shizuku.** { *; }

# Keep data classes
-keep class com.cosmoclicker.app.data.model.** { *; }

# Kotlin
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response

# With R8 full mode, generic signatures are preserved for string method arguments.
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <methods>;
}

# Keep Serialization classes
-keepattributes *Annotation*, InnerClasses
-dontwarn kotlinx.serialization.**
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `Module` and `Provides` annotated methods
-keepclassmembers,allowobfuscation class * {
  @dagger.Provides <methods>;
  @dagger.Module <fields>;
}

# Keep `Hilt*` classes
-keep class * extends *Hilt* { *; }

# Keep generated classes
-keep class * extends dagger.hilt.android.internal.managers.ComponentSupplier { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
