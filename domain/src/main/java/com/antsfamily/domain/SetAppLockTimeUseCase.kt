package com.antsfamily.domain

import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SetAppLockTimeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke() = try {
        val currentTimestamp = System.currentTimeMillis()
        repository.setLockTimestamp(currentTimestamp)
        UseCaseResult.Success(Unit)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}