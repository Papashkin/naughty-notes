package com.antsfamily.naughtynotes.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.GetIsPinSetUseCase
import com.antsfamily.domain.VerifyAppLockedUseCase
import com.antsfamily.domain.model.ErrorType
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.model.toType
import com.antsfamily.naughtynotes.presentation.util.toMinutesString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getIsPinSetUseCase: GetIsPinSetUseCase,
    private val verifyAppLockedUseCase: VerifyAppLockedUseCase,
) : ViewModel() {

    private val _navigationToHomeFlow = MutableSharedFlow<Unit>()
    val navigationToHomeFlow: SharedFlow<Unit> = _navigationToHomeFlow.asSharedFlow()

    private val _navigationToPinVerificationFlow = MutableSharedFlow<Unit>()
    val navigationToPinVerificationFlow: SharedFlow<Unit> = _navigationToPinVerificationFlow.asSharedFlow()

    private val _showAppLockSnackbarFlow = MutableSharedFlow<String>()
    val showAppLockSnackbarFlow: SharedFlow<String> = _showAppLockSnackbarFlow.asSharedFlow()

    private val _showErrorSnackbarFlow = MutableSharedFlow<ErrorType>()
    val showErrorSnackbarFlow: SharedFlow<ErrorType> = _showErrorSnackbarFlow.asSharedFlow()

    init {
        verifyAppLocked()
    }

    private fun verifyAppLocked() = viewModelScope.launch {
        delay(SPLASH_SCREEN_ANIMATION_DURATION.toLong())
        val result = verifyAppLockedUseCase()
        when (result) {
            is UseCaseResult.Success -> handleVerifyAppLockedSuccessResult(result.data)
            is UseCaseResult.Error -> handleVerifyAppLockedErrorResult(result.exception)
        }
    }

    private suspend fun handleVerifyAppLockedSuccessResult(result: Pair<Long, Boolean>) {
        val (remainTime, isLocked) = result
        if (isLocked) {
            handleAppIsLocked(remainTime)
        } else {
            getIsPinSet()
        }
    }

    private suspend fun handleVerifyAppLockedErrorResult(e: Exception) {
        _showErrorSnackbarFlow.emit(e.toType())
    }

    private suspend fun handleAppIsLocked(remainTime: Long) {
        _showAppLockSnackbarFlow.emit(remainTime.toMinutesString())
    }

    private suspend fun getIsPinSet() {
        val result = getIsPinSetUseCase()
        when (result) {
            is UseCaseResult.Error -> handleErrorPinResult(result.exception)
            is UseCaseResult.Success -> handleSuccessPinResult(result.data)
        }
    }

    private suspend fun handleSuccessPinResult(isPinSet: Boolean) {
        if (isPinSet) {
            _navigationToPinVerificationFlow.emit(Unit)
        } else {
            _navigationToHomeFlow.emit(Unit)
        }
    }

    private fun handleErrorPinResult(e: Exception) {
        Log.e(this::class.simpleName, e.message ?: e.toString())
    }
}