package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class VerifyPinCodeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(code: String): Boolean {
        return repository.verifyPinCode(code)
    }
}
