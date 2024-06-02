package com.example.timesdigest.ui.settings

import com.example.timesdigest.model.DarkModeConfig

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val settings: UserEditableSettings) : SettingsUiState
}

data class UserEditableSettings(
    val useDynamicColors: Boolean,
    val darkModeConfig: DarkModeConfig,
)
