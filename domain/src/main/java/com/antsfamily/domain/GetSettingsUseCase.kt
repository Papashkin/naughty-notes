package com.antsfamily.domain

import com.antsfamily.domain.model.SettingsModel
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(): UseCaseResult<SettingsModel> = try {
        val isDarkMode = repository.getIsDarkMode()
        val isPinCodeSet = repository.isPinCodeSet()
        val version = repository.getAppVersion()

        val settings = SettingsModel(
            isDarkMode = isDarkMode,
            isPinCodeSet = isPinCodeSet,
            appVersion = version
        )
        UseCaseResult.Success(settings)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}