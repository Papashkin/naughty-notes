package com.antsfamily.naughtynotes.presentation.pincode

data class PinCodeUiState(
    val isSaveButtonEnabled: Boolean = false,
    val isCodeVisible: Boolean = false,
    val code: String = ""
)