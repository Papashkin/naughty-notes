package com.antsfamily.domain

import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class VerifyPinCodeUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(code: String): UseCaseResult<Boolean> = try {
        val isVerified = repository.verifyPinCode(code)
        UseCaseResult.Success(isVerified)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}
