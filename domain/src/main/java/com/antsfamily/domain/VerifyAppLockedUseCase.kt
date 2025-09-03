package com.antsfamily.domain

import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.SettingsRepository
import javax.inject.Inject

private const val LOCK_TIME_MILLIS = 10 * 60 * 1000

class VerifyAppLockedUseCase @Inject constructor(
    private val repository: SettingsRepository
) {

    operator fun invoke(): UseCaseResult<Pair<Long, Boolean>> = try {
        val currentTimestamp = System.currentTimeMillis()
        val lockTimestamp = repository.getLockTimestamp()
        val elapsedTime = currentTimestamp - lockTimestamp

        val isLocked = elapsedTime < LOCK_TIME_MILLIS
        val remainingTime = LOCK_TIME_MILLIS - elapsedTime

        UseCaseResult.Success(remainingTime to isLocked)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}