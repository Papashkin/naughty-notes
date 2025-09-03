package com.antsfamily.domain

import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

class GetIsPinSetUseCase @Inject constructor(
    private val repository: SettingsRepository,
) {
    operator fun invoke(): UseCaseResult<Boolean> = try {
        val isPinSet = repository.isPinCodeSet()
        UseCaseResult.Success(isPinSet)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}