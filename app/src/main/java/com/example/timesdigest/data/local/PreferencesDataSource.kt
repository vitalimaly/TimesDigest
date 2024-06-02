package com.example.timesdigest.data.local

import androidx.datastore.core.DataStore
import com.example.timesdigest.data.local.datastore.DarkModeProto
import com.example.timesdigest.data.local.datastore.UserPreferences
import com.example.timesdigest.data.local.datastore.copy
import com.example.timesdigest.model.DarkModeConfig
import com.example.timesdigest.model.Settings
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val settings = userPreferences.data.map {
        Settings(
            useDynamicColors = it.useDynamicColors,
            darkModeConfig = when (it.darkMode) {
                null,
                DarkModeProto.UNRECOGNIZED,
                DarkModeProto.DARK_MODE_FOLLOW_SYSTEM -> DarkModeConfig.SYSTEM
                DarkModeProto.DARK_MODE_LIGHT -> DarkModeConfig.LIGHT
                DarkModeProto.DARK_MODE_DARK -> DarkModeConfig.DARK
            }
        )
    }

    suspend fun setDynamicColorsPreference(useDynamicColors: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.useDynamicColors = useDynamicColors
            }
        }
    }

    suspend fun setDarkModePreference(darkModeConfig: DarkModeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkMode = when (darkModeConfig) {
                    DarkModeConfig.SYSTEM -> DarkModeProto.DARK_MODE_FOLLOW_SYSTEM
                    DarkModeConfig.LIGHT -> DarkModeProto.DARK_MODE_LIGHT
                    DarkModeConfig.DARK -> DarkModeProto.DARK_MODE_DARK
                }
            }
        }
    }
}