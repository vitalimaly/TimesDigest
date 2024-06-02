package com.example.timesdigest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timesdigest.domain.ObserveSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MainViewModel @Inject constructor(
    observeSettingsUseCase: ObserveSettingsUseCase
) : ViewModel() {
    val uiState: StateFlow<MainUiState> = observeSettingsUseCase()
        .map { settings ->
            MainUiState.Success(settings)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = MainUiState.Loading,
        )
}
