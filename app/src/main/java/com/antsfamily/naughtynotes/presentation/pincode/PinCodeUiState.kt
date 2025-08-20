package com.antsfamily.naughtynotes.presentation.pincode

sealed class PinCodeUiState {
    data object Loading: PinCodeUiState()
    data class Content(
        val isSavePinButtonVisible: Boolean,
        val isCodeVisible: Boolean,
        val code: String
    ): PinCodeUiState()
    data object Error: PinCodeUiState()
}