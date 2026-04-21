package com.cosmoclicker.app.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat

object PermissionUtils {

    fun hasOverlayPermission(context: Context): Boolean {
        return Settings.canDrawOverlays(context)
    }

    fun requestOverlayPermission(context: Context) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${context.packageName}")
        )
        context.startActivity(intent)
    }

    fun hasAccessibilityPermission(context: Context): Boolean {
        val serviceName = "${context.packageName}/.service.AutoClickerService"
        val enabledServices = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: ""
        return enabledServices.contains(serviceName)
    }

    fun openAccessibilitySettings(context: Context) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun hasRequiredPermissions(context: Context): Boolean {
        return hasOverlayPermission(context) && 
               (hasAccessibilityPermission(context) || ShizukuHolder.isShizukuAvailable())
    }

    fun getMissingPermissions(context: Context): List<String> {
        val missing = mutableListOf<String>()
        
        if (!hasOverlayPermission(context)) {
            missing.add("overlay")
        }
        
        if (!hasAccessibilityPermission(context) && !ShizukuHolder.isShizukuAvailable()) {
            missing.add("accessibility")
        }
        
        return missing
    }
}
