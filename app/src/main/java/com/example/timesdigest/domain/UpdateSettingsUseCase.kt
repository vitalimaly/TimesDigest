package com.example.timesdigest.domain

import com.example.timesdigest.data.SettingsRepository
import com.example.timesdigest.model.DarkModeConfig
import javax.inject.Inject

class UpdateSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend fun setDynamicColorsPreference(useDynamicColors: Boolean) {
        settingsRepository.setDynamicColorsPreference(useDynamicColors)
    }

    suspend fun setDarkModePreference(darkModeConfig: DarkModeConfig) {
        settingsRepository.setDarkModePreference(darkModeConfig)
    }
}