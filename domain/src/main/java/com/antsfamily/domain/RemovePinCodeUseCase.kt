package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class RemovePinCodeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    operator fun invoke() {
        return repository.removePinCode()
    }
}