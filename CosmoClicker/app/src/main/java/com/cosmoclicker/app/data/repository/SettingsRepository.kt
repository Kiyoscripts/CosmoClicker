package com.cosmoclicker.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.cosmoclicker.app.data.model.ClickPattern
import com.cosmoclicker.app.data.model.PatternType
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val json = Json { ignoreUnknownKeys = true }

    val currentPatternFlow: Flow<ClickPattern> = dataStore.data.map { preferences ->
        val patternJson = preferences[PATTERN_JSON_KEY]
        patternJson?.let { decodePattern(it) } ?: createDefaultPattern()
    }

    val clickIntervalFlow: Flow<Long> = dataStore.data.map { preferences ->
        preferences[CLICK_INTERVAL_KEY] ?: 1000L
    }

    val darkModeFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    val dynamicColorFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DYNAMIC_COLOR_KEY] ?: true
    }

    val hapticFeedbackFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[HAPTIC_FEEDBACK_KEY] ?: true
    }

    val presetsFlow: Flow<List<String>> = dataStore.data.map { preferences ->
        val presetsJson = preferences[PRESETS_KEY]
        presetsJson?.let {
            json.decodeFromString<List<String>>(it)
        } ?: emptyList()
    }

    suspend fun getCurrentPattern(): ClickPattern {
        return dataStore.data.first()[PATTERN_JSON_KEY]
            ?.let { decodePattern(it) }
            ?: createDefaultPattern()
    }

    suspend fun setCurrentPattern(pattern: ClickPattern) {
        dataStore.edit { preferences ->
            preferences[PATTERN_JSON_KEY] = encodePattern(pattern)
        }
    }

    suspend fun setClickInterval(interval: Long) {
        dataStore.edit { preferences ->
            preferences[CLICK_INTERVAL_KEY] = interval
        }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    suspend fun setDynamicColor(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DYNAMIC_COLOR_KEY] = enabled
        }
    }

    suspend fun setHapticFeedback(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[HAPTIC_FEEDBACK_KEY] = enabled
        }
    }

    suspend fun savePreset(name: String, pattern: ClickPattern) {
        val currentPresets = presetsFlow.first()
        val presetData = json.encodeToString(
            mapOf(
                "id" to pattern.id,
                "name" to name,
                "pattern" to json.encodeToString(pattern)
            )
        )
        val newPresets = currentPresets.toMutableList().apply { add(presetData) }
        dataStore.edit { preferences ->
            preferences[PRESETS_KEY] = json.encodeToString(newPresets)
        }
    }

    suspend fun deletePreset(presetIndex: Int) {
        val currentPresets = presetsFlow.first().toMutableList()
        if (presetIndex in currentPresets.indices) {
            currentPresets.removeAt(presetIndex)
            dataStore.edit { preferences ->
                preferences[PRESETS_KEY] = json.encodeToString(currentPresets)
            }
        }
    }

    suspend fun getPresets(): List<Map<String, String>> {
        return presetsFlow.first().map { json.decodeFromString<Map<String, String>>(it) }
    }

    private fun encodePattern(pattern: ClickPattern): String {
        return json.encodeToString(pattern)
    }

    private fun decodePattern(jsonString: String): ClickPattern {
        return json.decodeFromString(jsonString)
    }

    private fun createDefaultPattern(): ClickPattern {
        return ClickPattern(
            name = "Default",
            type = PatternType.SINGLE_POINT,
            intervalMs = 1000
        )
    }

    companion object {
        private val PATTERN_JSON_KEY = stringPreferencesKey("pattern_json")
        private val CLICK_INTERVAL_KEY = longPreferencesKey("click_interval")
        private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        private val DYNAMIC_COLOR_KEY = booleanPreferencesKey("dynamic_color")
        private val HAPTIC_FEEDBACK_KEY = booleanPreferencesKey("haptic_feedback")
        private val PRESETS_KEY = stringPreferencesKey("presets")
    }
}
