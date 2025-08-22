package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class GetIsPinSetUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): Boolean {
        return repository.isPinCodeSet()
    }
}