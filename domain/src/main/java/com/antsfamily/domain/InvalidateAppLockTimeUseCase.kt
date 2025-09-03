package com.antsfamily.domain

import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class InvalidateAppLockTimeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke() = try {
        repository.invalidateLockTimestamp()
        UseCaseResult.Success(Unit)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}