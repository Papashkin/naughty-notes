package com.antsfamily.naughtynotes.presentation.verifypincode

import com.antsfamily.naughtynotes.presentation.verifypincode.model.VerificationErrorType

data class PinCodeVerificationUiState(
    val code: String = "",
    val isCodeVisible: Boolean = false,
    val isProceedButtonEnabled: Boolean = false,
    val isProceedButtonLoadingVisible: Boolean = false,
    val isErrorVisible: Boolean = false,
    val errorType: VerificationErrorType? = null,
)
