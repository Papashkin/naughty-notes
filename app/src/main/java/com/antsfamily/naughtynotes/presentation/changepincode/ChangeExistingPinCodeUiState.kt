package com.antsfamily.naughtynotes.presentation.changepincode

import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinCodeButtonState
import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinCodeStep
import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinErrorType

data class ChangeExistingPinCodeUiState(
    val code: String = "",
    val step: ChangePinCodeStep = ChangePinCodeStep.EXISTED_CODE,
    val isCodeVisible: Boolean = false,
    val proceedButtonState: ChangePinCodeButtonState = ChangePinCodeButtonState.PROCEED,
    val isProceedButtonEnabled: Boolean = false,
    val isProceedButtonLoadingVisible: Boolean = false,
    val isErrorVisible: Boolean = false,
    val errorType: ChangePinErrorType? = null,
)
