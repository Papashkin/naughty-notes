package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SetAppLockTimeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke() {
        val currentTimestamp = System.currentTimeMillis()
        return repository.setLockTimestamp(currentTimestamp)
    }
}