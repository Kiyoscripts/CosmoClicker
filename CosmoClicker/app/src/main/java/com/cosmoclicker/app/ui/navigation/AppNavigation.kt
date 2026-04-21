package com.cosmoclicker.app.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cosmoclicker.app.ui.screens.HomeScreen
import com.cosmoclicker.app.ui.screens.PatternsScreen
import com.cosmoclicker.app.ui.screens.PresetsScreen
import com.cosmoclicker.app.ui.screens.SettingsScreen

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Patterns : Screen("patterns", "Patterns")
    object Presets : Screen("presets", "Presets")
    object Settings : Screen("settings", "Settings")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    Screen.Home,
                    Screen.Patterns,
                    Screen.Presets,
                    Screen.Settings
                )
                items { screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = getIconForScreen(screen),
                                contentDescription = screen.title
                            )
                        },
                        label = { Text(screen.title) },
                        selected = navBackStackEntry?.destination?.hierarchy?.any { 
                            it.route == screen.route 
                        } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onStartClick = { },
                    onNavigateToPatterns = {
                        navController.navigate(Screen.Patterns.route)
                    }
                )
            }
            composable(Screen.Patterns.route) {
                PatternsScreen()
            }
            composable(Screen.Presets.route) {
                PresetsScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun getIconForScreen(screen: Screen): ImageVector {
    return when (screen) {
        Screen.Home -> androidx.compose.material.icons.Icons.Filled.Home
        Screen.Patterns -> androidx.compose.material.icons.Icons.Filled.Smartphone
        Screen.Presets -> androidx.compose.material.icons.Icons.Filled.Bookmark
        Screen.Settings -> androidx.compose.material.icons.Icons.Filled.Settings
    }
}
