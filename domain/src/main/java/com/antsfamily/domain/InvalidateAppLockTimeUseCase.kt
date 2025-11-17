package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InvalidateAppLockTimeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        repository.invalidateLockTimestamp()
    }
}