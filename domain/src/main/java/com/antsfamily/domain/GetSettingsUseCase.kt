package com.antsfamily.domain

import com.antsfamily.domain.model.SettingsModel
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(): SettingsModel {
        val isDarkMode = repository.getIsDarkMode()
        val isPinCodeSet = repository.isPinCodeSet()

        return SettingsModel(
            isDarkMode = isDarkMode,
            isPinCodeSet = isPinCodeSet
        )
    }
}