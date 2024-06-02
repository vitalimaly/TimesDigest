package com.example.timesdigest.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timesdigest.domain.ObserveSettingsUseCase
import com.example.timesdigest.domain.UpdateSettingsUseCase
import com.example.timesdigest.model.DarkModeConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SettingsViewModel @Inject constructor(
    observeSettingsUseCase: ObserveSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase
) : ViewModel() {
    val uiState: StateFlow<SettingsUiState> = observeSettingsUseCase()
        .map { settings ->
            SettingsUiState.Success(
                settings = UserEditableSettings(
                    useDynamicColors = settings.useDynamicColors,
                    darkModeConfig = settings.darkModeConfig,
                ),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = SettingsUiState.Loading,
        )

    fun setDarkModePreference(darkModeConfig: DarkModeConfig) {
        viewModelScope.launch {
            updateSettingsUseCase.setDarkModePreference(darkModeConfig)
        }
    }

    fun setDynamicColorsPreference(useDynamicColors: Boolean) {
        viewModelScope.launch {
            updateSettingsUseCase.setDynamicColorsPreference(useDynamicColors)
        }
    }
}
