package com.cosmoclicker.app.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.cosmoclicker.app.data.repository.SettingsRepository
import com.cosmoclicker.app.util.ShizukuHolder
import javax.inject.Singleton

private const val SETTINGS_NAME = "settings"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_NAME)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context.dataStore)
    }

    @Provides
    @Singleton
    fun provideShizukuHolder(): ShizukuHolder {
        return ShizukuHolder
    }
}
