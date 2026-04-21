package com.cosmoclicker.app.ui.screens

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cosmoclicker.app.util.ShizukuHolder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var darkMode by remember { mutableStateOf(false) }
    var dynamicColor by remember { mutableStateOf(true) }
    var hapticFeedback by remember { mutableStateOf(true) }
    var shizukuAvailable by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        shizukuAvailable = ShizukuHolder.isShizukuAvailable()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        SettingsSection(title = "Appearance") {
            SettingsSwitch(
                title = "Dark Mode",
                subtitle = "Use dark theme",
                checked = darkMode,
                onCheckedChange = { darkMode = it },
                icon = Icons.Default.DarkMode
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                SettingsSwitch(
                    title = "Dynamic Color",
                    subtitle = "Match system colors (Material You)",
                    checked = dynamicColor,
                    onCheckedChange = { dynamicColor = it },
                    icon = Icons.Default.Palette
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        SettingsSection(title = "Feedback") {
            SettingsSwitch(
                title = "Haptic Feedback",
                subtitle = "Vibrate on click",
                checked = hapticFeedback,
                onCheckedChange = { hapticFeedback = it },
                icon = Icons.Default.Vibration
            )
        }

        Spacer(Modifier.height(16.dp))

        SettingsSection(title = "Advanced") {
            SettingsItem(
                title = "Shizuku Status",
                subtitle = if (shizukuAvailable) "Available" else "Not available",
                icon = if (shizukuAvailable) Icons.Default.CheckCircle else Icons.Default.Error,
                iconTint = if (shizukuAvailable) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        SettingsSection(title = "About") {
            SettingsItem(
                title = "Version",
                subtitle = "1.0.0",
                icon = Icons.Default.Info
            )

            SettingsItem(
                title = "Open Source Licenses",
                subtitle = "View licenses",
                icon = Icons.Default.Description
            )
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "CosmoClicker",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Made with ❤️",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        content()
    }
}

@Composable
fun SettingsSwitch(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 4.dp),
        onClick = { onCheckedChange(!checked) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
