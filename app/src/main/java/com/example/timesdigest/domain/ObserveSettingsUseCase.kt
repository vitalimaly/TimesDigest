package com.example.timesdigest.domain

import com.example.timesdigest.data.SettingsRepository
import javax.inject.Inject

class ObserveSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke() = settingsRepository.settings
}