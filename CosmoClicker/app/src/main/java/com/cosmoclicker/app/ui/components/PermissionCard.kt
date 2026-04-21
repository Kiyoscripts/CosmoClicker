package com.cosmoclicker.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cosmoclicker.app.util.PermissionUtils
import com.cosmoclicker.app.util.ShizukuHolder

@Composable
fun PermissionCard(
    modifier: Modifier = Modifier,
    onGrantOverlay: () -> Unit,
    onGrantAccessibility: () -> Unit
) {
    var hasOverlay by remember { mutableStateOf(false) }
    var hasAccessibility by remember { mutableStateOf(false) }
    var shizukuAvailable by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        hasOverlay = PermissionUtils.hasOverlayPermission(
            androidx.compose.ui.platform.LocalContext.current
        )
        hasAccessibility = PermissionUtils.hasAccessibilityPermission(
            androidx.compose.ui.platform.LocalContext.current
        )
        shizukuAvailable = ShizukuHolder.isShizukuAvailable()
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Permissions Required",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(16.dp))

            PermissionRow(
                label = "Overlay Permission",
                isGranted = hasOverlay,
                onGrant = onGrantOverlay
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
            )

            PermissionRow(
                label = "Accessibility Service",
                isGranted = hasAccessibility || shizukuAvailable,
                onGrant = onGrantAccessibility,
                showGrantButton = !hasAccessibility && !shizukuAvailable
            )

            if (shizukuAvailable) {
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Shizuku Active",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun PermissionRow(
    label: String,
    isGranted: Boolean,
    showGrantButton: Boolean = true,
    onGrant: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isGranted) Icons.Default.CheckCircle else Icons.Default.Error,
                contentDescription = null,
                tint = if (isGranted) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (showGrantButton && !isGranted) {
            TextButton(onClick = onGrant) {
                Text("Grant")
            }
        } else if (isGranted) {
            Text(
                text = "Granted",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
