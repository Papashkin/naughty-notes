package com.antsfamily.naughtynotes.presentation.changepincode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SavePinUseCase
import com.antsfamily.domain.VerifyPinCodeUseCase
import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinCodeButtonState
import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinCodeStep
import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinErrorType
import com.antsfamily.naughtynotes.presentation.util.PIN_CODE_SIZE
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
class ChangeExistingPinCodeViewModel @Inject constructor(
    private val verifyPinCodeUseCase: VerifyPinCodeUseCase,
    private val savePinUseCase: SavePinUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ChangeExistingPinCodeUiState())
    val state: StateFlow<ChangeExistingPinCodeUiState>
        get() = _state.asStateFlow()

    private val _navigateBackFlow = MutableSharedFlow<Unit>()
    val navigateBackFlow: SharedFlow<Unit>
        get() = _navigateBackFlow.asSharedFlow()

    private val _showSuccessfulPinSaveFlow = MutableSharedFlow<Unit>()
    val showSuccessfulPinSaveFlow: SharedFlow<Unit>
        get() = _showSuccessfulPinSaveFlow.asSharedFlow()

    private var newCode: String = ""

    fun onKeyClicked(value: Int) = viewModelScope.launch {
        _state.update {
            val newCode = it.code.plus(value).take(PIN_CODE_SIZE)
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
            val newCode = it.code.dropLast(1)
            it.copy(
                code = newCode,
                isErrorVisible = false,
                errorType = null,
                isProceedButtonEnabled = newCode.length == PIN_CODE_SIZE
            )
        }
    }

    fun onShowCodeClicked() {
        _state.update {
            it.copy(isCodeVisible = !it.isCodeVisible)
        }
    }

    fun onProceedClicked() = viewModelScope.launch {
        when (_state.value.step) {
            ChangePinCodeStep.EXISTED_CODE -> verifyExistedPinCode()

            ChangePinCodeStep.NEW_CODE -> {
                newCode = _state.value.code
                _state.update {
                    it.copy(
                        code = "",
                        step = ChangePinCodeStep.REPEAT_NEW_CODE,
                        proceedButtonState = ChangePinCodeButtonState.SAVE,
                        isProceedButtonLoadingVisible = false,
                        isProceedButtonEnabled = false
                    )
                }
            }

            ChangePinCodeStep.REPEAT_NEW_CODE -> verifyNewPinCode()
        }
    }

    private suspend fun verifyExistedPinCode() {
        _state.update { it.copy(isProceedButtonLoadingVisible = true) }

        try {
            val code = _state.value.code
            val result = verifyPinCodeUseCase(code = code)
            handleVerifyPinCodeSuccessResult(result)
        } catch (e: Exception) {
            handleVerifyPinCodeErrorResult()
        } finally {

        }
    }

    private fun handleVerifyPinCodeSuccessResult(isVerified: Boolean) {
        if (isVerified) {
            _state.update {
                it.copy(
                    code = "",
                    step = ChangePinCodeStep.NEW_CODE,
                    isProceedButtonLoadingVisible = false,
                    isProceedButtonEnabled = false
                )
            }
        } else {
            _state.update {
                it.copy(
                    isProceedButtonLoadingVisible = false,
                    isErrorVisible = true,
                    errorType = ChangePinErrorType.WRONG_PIN
                )
            }
        }
    }

    private fun handleVerifyPinCodeErrorResult() {
        _state.update {
            it.copy(
                isErrorVisible = true,
                isProceedButtonLoadingVisible = false,
                isProceedButtonEnabled = false,
                errorType = ChangePinErrorType.UNKNOWN
            )
        }
    }

    private suspend fun verifyNewPinCode() {
        if (_state.value.code != newCode) {
            _state.update {
                it.copy(
                    isErrorVisible = true,
                    errorType = ChangePinErrorType.PINS_NOT_MATCH,
                )
            }
        } else {
            savePinUseCase(newCode)
            _showSuccessfulPinSaveFlow.emit(Unit)
        }
    }
}