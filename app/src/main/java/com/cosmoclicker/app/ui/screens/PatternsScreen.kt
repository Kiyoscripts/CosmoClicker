package com.cosmoclicker.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cosmoclicker.app.data.model.ClickPattern
import com.cosmoclicker.app.data.model.PatternType
import com.cosmoclicker.app.ui.theme.BluePrimary
import com.cosmoclicker.app.ui.theme.YellowAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatternsScreen() {
    var selectedPattern by remember { mutableStateOf(PatternType.SINGLE_POINT) }
    var intervalMs by remember { mutableStateOf(1000L) }
    var clickCount by remember { mutableStateOf(0) }
    var points by remember { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }
    var showAddPointDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = "Click Patterns",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Pattern Type",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(12.dp))

                PatternType.values().forEach { patternType ->
                    PatternTypeItem(
                        patternType = patternType,
                        isSelected = selectedPattern == patternType,
                        onClick = { selectedPattern = patternType }
                    )
                    if (patternType != PatternType.values().last()) {
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Click Interval",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Slider(
                    value = intervalMs.toFloat(),
                    onValueChange = { intervalMs = it.toLong() },
                    valueRange = 50f..10000f,
                    steps = 20
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "50ms",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${intervalMs}ms",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = BluePrimary
                    )
                    Text(
                        text = "10s",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Click Count",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = if (clickCount == 0) "Infinite" else clickCount.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row {
                        IconButton(onClick = {
                            if (clickCount > 0) clickCount -= 10
                        }) {
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease"
                            )
                        }

                        IconButton(onClick = {
                            clickCount += 10
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase"
                            )
                        }
                    }
                }
            }
        }

        if (selectedPattern == PatternType.MULTI_POINT) {
            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Click Points",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${points.size} points",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    points.forEachIndexed { index, point ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Point ${index + 1}: (${point.first}, ${point.second})")
                            IconButton(onClick = {
                                points = points.filterIndexed { i, _ -> i != index }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { showAddPointDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = YellowAccent
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddLocation,
                            contentDescription = "Add Point"
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Add Point")
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Save Pattern", style = MaterialTheme.typography.titleMedium)
        }
    }

    if (showAddPointDialog) {
        AddPointDialog(
            onDismiss = { showAddPointDialog = false },
            onConfirm = { x, y ->
                points = points + (x to y)
                showAddPointDialog = false
            }
        )
    }
}

@Composable
fun PatternTypeItem(
    patternType: PatternType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = { Text(patternType.displayName) },
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        } else null,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun AddPointDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    var x by remember { mutableStateOf("0") }
    var y by remember { mutableStateOf("0") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Click Point") },
        text = {
            Column {
                OutlinedTextField(
                    value = x,
                    onValueChange = { x = it },
                    label = { Text("X Coordinate") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = y,
                    onValueChange = { y = it },
                    label = { Text("Y Coordinate") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(x.toIntOrNull() ?: 0, y.toIntOrNull() ?: 0)
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

val PatternType.displayName: String
    get() = when (this) {
        PatternType.SINGLE_POINT -> "Single Point"
        PatternType.MULTI_POINT -> "Multi Point"
        PatternType.FOLLOW_TOUCH -> "Follow Touch"
        PatternType.INTERVAL_BURST -> "Interval Burst"
        PatternType.SHIZUKU_MODE -> "Shizuku Mode"
    }
