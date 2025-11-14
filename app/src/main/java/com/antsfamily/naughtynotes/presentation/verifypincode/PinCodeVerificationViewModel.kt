package com.antsfamily.naughtynotes.presentation.verifypincode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.InvalidateAppLockTimeUseCase
import com.antsfamily.domain.SetAppLockTimeUseCase
import com.antsfamily.domain.VerifyPinCodeUseCase
import com.antsfamily.naughtynotes.presentation.util.PIN_CODE_SIZE
import com.antsfamily.naughtynotes.presentation.verifypincode.model.VerificationErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PinCodeVerificationViewModel @Inject constructor(
    private val verifyPinCodeUseCase: VerifyPinCodeUseCase,
    private val setAppLockTimeUseCase: SetAppLockTimeUseCase,
    private val invalidateAppLockTimeUseCase: InvalidateAppLockTimeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PinCodeVerificationUiState())
    val state: StateFlow<PinCodeVerificationUiState>
        get() = _state.asStateFlow()

    private val _navigateToHomeFlow = MutableSharedFlow<Unit>()
    val navigateToHomeFlow: SharedFlow<Unit>
        get() = _navigateToHomeFlow.asSharedFlow()

    private var attempts: Int = 0
    private var isLocked: Boolean = false

    fun onKeyClicked(value: Int) {
        if (isLocked) return

        _state.update {
            val newCode = it.code.plus(value).take(PIN_CODE_SIZE)
            it.copy(
                code = newCode,
                isErrorVisible = false,
                errorType = null,
                isProceedButtonEnabled = !isLocked && newCode.length == PIN_CODE_SIZE
            )
        }
    }

    fun onDeleteClicked() {
        if (isLocked) return

        _state.update {
            val newCode = it.code.dropLast(1)
            it.copy(
                code = newCode,
                isProceedButtonEnabled = newCode.length == PIN_CODE_SIZE
            )
        }
    }

    fun onShowCodeClicked() {
        if (isLocked) return

        _state.update {
            it.copy(isCodeVisible = !it.isCodeVisible)
        }
    }

    fun onProceedClicked() = viewModelScope.launch {
        if (isLocked) return@launch
        try {
            showLoading()
            val code = _state.value.code
            val result = verifyPinCodeUseCase(code = code)
            handleVerifyPinCodeSuccessResult(result)
        } catch (e: Exception) {
            handleVerifyPinCodeErrorResult(e)
        } finally {
            hideLoading()
        }
    }

    private fun showLoading() {
        _state.update { it.copy(isProceedButtonLoadingVisible = true) }
    }

    private fun hideLoading() {
        _state.update { it.copy(isProceedButtonLoadingVisible = false) }
    }

    private suspend fun handleVerifyPinCodeSuccessResult(isVerified: Boolean) {
        if (isVerified) {
            invalidateClockAndProceed()
        } else {
            handleErrorAttempt()
        }
    }

    private suspend fun invalidateClockAndProceed() {
        try {
            invalidateAppLockTimeUseCase()
        } catch (e: Exception) {
            Log.e(this::class.simpleName, e.message ?: e.toString())
        } finally {
            _navigateToHomeFlow.emit(Unit)
        }
    }

    private fun handleVerifyPinCodeErrorResult(e: Exception) {
        //TODO think about do we need this
    }

    private fun handleErrorAttempt() {
        attempts++

        val errorType = when (attempts) {
            1 -> VerificationErrorType.FIRST_ATTEMPT
            2 -> VerificationErrorType.SECOND_ATTEMPT
            3 -> VerificationErrorType.LAST_ATTEMPT
            else -> return
        }

        if (errorType == VerificationErrorType.LAST_ATTEMPT) {
            setAppLockTimeUseCase()
            isLocked = true
        }

        //TODO implement to save lock time in SharedPrefs

        _state.update {
            it.copy(
                code = "",
                isErrorVisible = true,
                isProceedButtonLoadingVisible = false,
                isProceedButtonEnabled = false,
                errorType = errorType
            )
        }
    }
}