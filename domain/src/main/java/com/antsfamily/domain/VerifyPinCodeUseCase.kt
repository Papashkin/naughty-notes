package com.antsfamily.domain

import com.antsfamily.domain.repository.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VerifyPinCodeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    suspend operator fun invoke(code: String): Boolean = withContext(Dispatchers.IO) {
        repository.verifyPinCode(code)
    }
}
