package com.antsfamily.naughtynotes.presentation.verifypincode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.VerifyPinCodeUseCase
import com.antsfamily.naughtynotes.presentation.util.PIN_CODE_SIZE
import com.antsfamily.naughtynotes.presentation.verifypincode.model.VerificationErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val verifyPinCodeUseCase: VerifyPinCodeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PinCodeVerificationUiState())
    val state: StateFlow<PinCodeVerificationUiState>
        get() = _state.asStateFlow()

    private val _navigateToHomeFlow = MutableSharedFlow<Unit>()
    val navigateToHomeFlow: SharedFlow<Unit>
        get() = _navigateToHomeFlow.asSharedFlow()

    private var attempts: Int = 0

    fun onKeyClicked(value: Int) = viewModelScope.launch {
        _state.update {
            val newCode = it.code.plus(value)
            it.copy(
                code = newCode,
                isErrorVisible = false,
                errorType = null,
                isProceedButtonEnabled = newCode.length == PIN_CODE_SIZE
            )
        }
    }

    fun onDeleteClicked() {
        _state.update {
            it.copy(code = it.code.dropLast(1))
        }
    }

    fun onShowCodeClicked() {
        _state.update {
            it.copy(isCodeVisible = !it.isCodeVisible)
        }
    }

    fun onProceedClicked() = viewModelScope.launch {
        _state.update {
            it.copy(isProceedButtonLoadingVisible = true)
        }
        delay(500)
        val code = _state.value.code
        val isVerified = verifyPinCodeUseCase(code = code)
        if (isVerified) {
            _navigateToHomeFlow.emit(Unit)
        } else {
            attempts++
            handleErrorAttempt()
        }
    }

    private fun handleErrorAttempt() {
        val errorType = when (attempts) {
            0 -> return
            1 -> VerificationErrorType.FIRST_ATTEMPT
            2 -> VerificationErrorType.SECOND_ATTEMPT
            else -> VerificationErrorType.LAST_ATTEMPT
        }

        //TODO implement to save lock time in SharedPrefs

        _state.update {
            it.copy(
                code = "",
                isErrorVisible = true,
                isProceedButtonLoadingVisible = false,
                errorType = errorType
            )
        }
    }
}