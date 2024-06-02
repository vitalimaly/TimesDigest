package com.example.timesdigest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.timesdigest.model.DarkModeConfig
import com.example.timesdigest.ui.TimesDigestApp
import com.example.timesdigest.ui.theme.TimesDigestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var uiState: MainUiState by mutableStateOf(MainUiState.Loading)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.onEach {
                    uiState = it
                }.collect()
            }
        }
        setContent {
            TimesDigestTheme(
                darkMode = shouldUseDarkMode(uiState),
                useDynamicColors = shouldUseDynamicColors(uiState)
            ) {
                TimesDigestApp()
            }
        }
    }

    @Composable
    fun shouldUseDarkMode(uiState: MainUiState) = when (uiState) {
        is MainUiState.Loading -> false
        is MainUiState.Success -> when (uiState.settings.darkModeConfig) {
            DarkModeConfig.SYSTEM -> isSystemInDarkTheme()
            DarkModeConfig.LIGHT -> false
            DarkModeConfig.DARK -> true
        }
    }

    private fun shouldUseDynamicColors(uiState: MainUiState) = when (uiState) {
        is MainUiState.Loading -> false
        is MainUiState.Success -> uiState.settings.useDynamicColors
    }
}
