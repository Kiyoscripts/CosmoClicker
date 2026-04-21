package com.cosmoclicker.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ClickPoint(
    val x: Int,
    val y: Int
)

@Serializable
enum class PatternType {
    SINGLE_POINT,
    MULTI_POINT,
    FOLLOW_TOUCH,
    INTERVAL_BURST,
    SHIZUKU_MODE
}

@Serializable
data class ClickPattern(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val type: PatternType,
    val intervalMs: Long = 1000,
    val points: List<ClickPoint> = emptyList(),
    val clickCount: Int = 0, // 0 = infinite
    val isEnabled: Boolean = true
)

@Serializable
data class Preset(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String,
    val pattern: ClickPattern,
    val createdAt: Long = System.currentTimeMillis()
)
