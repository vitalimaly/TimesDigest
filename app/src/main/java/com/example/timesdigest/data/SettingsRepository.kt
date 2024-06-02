package com.example.timesdigest.data

import com.example.timesdigest.data.local.PreferencesDataSource
import com.example.timesdigest.model.DarkModeConfig
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) {
    val settings = preferencesDataSource.settings

    suspend fun setDynamicColorsPreference(useDynamicColors: Boolean) {
        preferencesDataSource.setDynamicColorsPreference(useDynamicColors)
    }

    suspend fun setDarkModePreference(darkModeConfig: DarkModeConfig) {
        preferencesDataSource.setDarkModePreference(darkModeConfig)
    }
}
