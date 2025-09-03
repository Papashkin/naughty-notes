package com.antsfamily.domain

import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class SavePinUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(code: String) = try {
        repository.setPinCode(code)
        UseCaseResult.Success(Unit)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}