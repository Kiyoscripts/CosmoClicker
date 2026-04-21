package com.cosmoclicker.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = BlueLight,
    secondary = YellowAccent,
    tertiary = YellowLight,
    background = Gray900,
    surface = Gray800,
    onPrimary = Gray900,
    onSecondary = Gray900,
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = BluePrimary,
    secondary = YellowAccent,
    tertiary = YellowDark,
    background = Gray100,
    surface = White,
    onPrimary = White,
    onSecondary = Gray900,
    onBackground = Gray900,
    onSurface = Gray900
)

@Composable
fun CosmoClickerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context).copy(
                    primary = BlueLight,
                    secondary = YellowAccent
                )
            } else {
                dynamicLightColorScheme(context).copy(
                    primary = BluePrimary,
                    secondary = YellowAccent
                )
            }
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
