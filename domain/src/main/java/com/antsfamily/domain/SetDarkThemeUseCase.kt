package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SetDarkThemeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(isDarkTheme: Boolean) {
        return repository.setIsDarkMode(isDarkTheme)
    }
}