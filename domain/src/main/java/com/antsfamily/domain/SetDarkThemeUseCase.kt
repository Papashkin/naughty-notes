package com.antsfamily.domain

import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SetDarkThemeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(isDarkTheme: Boolean) = try {
        repository.setIsDarkMode(isDarkTheme)
        UseCaseResult.Success(Unit)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}