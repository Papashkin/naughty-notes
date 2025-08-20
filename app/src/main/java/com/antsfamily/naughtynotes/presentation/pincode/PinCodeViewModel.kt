package com.antsfamily.naughtynotes.presentation.pincode

import androidx.lifecycle.ViewModel
import com.antsfamily.naughtynotes.presentation.util.PIN_CODE_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PinCodeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<PinCodeUiState>(PinCodeUiState.Loading)
    val state: StateFlow<PinCodeUiState> get() = _state.asStateFlow()

    init {
        _state.value = PinCodeUiState.Content(
            isSavePinButtonVisible = false,
            isCodeVisible = false,
            code = "",
        )
    }

    fun onKeyClicked(value: Int) {
        _state.update {
            when (it) {
                is PinCodeUiState.Content -> {
                    val newCode = it.code.plus(value)
                    it.copy(
                        code = newCode,
                        isSavePinButtonVisible = newCode.length == PIN_CODE_SIZE
                    )
                }
                else -> it
            }
        }
    }

    fun onDeleteClicked() {
        _state.update {
            when (it) {
                is PinCodeUiState.Content -> it.copy(
                    code = it.code.dropLast(1),
                    isSavePinButtonVisible = false
                )
                else -> it
            }
        }
    }

    fun onShowCodeClicked() {
        _state.update {
            when (it) {
                is PinCodeUiState.Content -> it.copy(isCodeVisible = !it.isCodeVisible)
                else -> it
            }
        }
    }
}