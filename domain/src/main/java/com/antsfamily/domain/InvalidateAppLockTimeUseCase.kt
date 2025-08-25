package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class InvalidateAppLockTimeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke() = repository.invalidateLockTimestamp()
}