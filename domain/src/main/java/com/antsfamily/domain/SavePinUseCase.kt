package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SavePinUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(code: String) {
        return repository.setPinCode(code)
    }
}