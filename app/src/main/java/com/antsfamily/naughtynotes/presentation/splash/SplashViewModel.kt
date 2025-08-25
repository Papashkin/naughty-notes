package com.antsfamily.naughtynotes.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.GetIsPinSetUseCase
import com.antsfamily.domain.VerifyAppLockedUseCase
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

    init {
        verifyAppLocked()
    }

    private fun verifyAppLocked() = viewModelScope.launch {
        delay(300)
        val (remainTime, isLocked) = verifyAppLockedUseCase()
        if (isLocked) {
            handleAppIsLocked(remainTime)
        } else {
            getIsPinSet()
        }
    }

    private suspend fun handleAppIsLocked(remainTime: Long) {
        _showAppLockSnackbarFlow.emit(remainTime.toMinutesString())
    }

    private suspend fun getIsPinSet() {

        val isPinSet = getIsPinSetUseCase()
        if (isPinSet) {
            _navigationToPinVerificationFlow.emit(Unit)
        } else {
            _navigationToHomeFlow.emit(Unit)
        }
    }
}