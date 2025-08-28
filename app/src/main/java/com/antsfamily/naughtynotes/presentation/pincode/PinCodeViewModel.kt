package com.antsfamily.naughtynotes.presentation.pincode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SavePinUseCase
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
class PinCodeViewModel @Inject constructor(
    private val savePinUseCase: SavePinUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(PinCodeUiState())
    val state: StateFlow<PinCodeUiState> get() = _state.asStateFlow()

    private val _showSuccessfulPinSaveFlow = MutableSharedFlow<Unit>()
    val showSuccessfulPinSaveFlow: SharedFlow<Unit>
        get() = _showSuccessfulPinSaveFlow.asSharedFlow()

    private val _dismissDialogFlow = MutableSharedFlow<Unit>()
    val dismissDialogFlow: SharedFlow<Unit>
        get() = _dismissDialogFlow.asSharedFlow()

    fun onKeyClicked(value: Int) {
        _state.update {
            val newCode = it.code.plus(value).take(PIN_CODE_SIZE)
            it.copy(
                code = newCode,
                isSaveButtonEnabled = newCode.length == PIN_CODE_SIZE
            )
        }
    }

    fun onDeleteClicked() {
        _state.update {
            it.copy(
                code = it.code.dropLast(1),
                isSaveButtonEnabled = false
            )
        }
    }

    fun onShowCodeClicked() {
        _state.update {
            it.copy(isCodeVisible = !it.isCodeVisible)
        }
    }

    fun onSaveButtonClicked() = viewModelScope.launch {
        if (_state.value.code.isNotBlank()) {
            savePinUseCase(_state.value.code)
            _showSuccessfulPinSaveFlow.emit(Unit)
            invalidateState()
        }
    }

    fun onDialogDismissed() = viewModelScope.launch {
        _dismissDialogFlow.emit(Unit)
        invalidateState()
    }

    private fun invalidateState() {
        _state.value = PinCodeUiState()
    }
}