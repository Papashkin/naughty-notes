package com.antsfamily.naughtynotes.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.GetIsPinSetUseCase
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
) : ViewModel() {

    private val _navigationToHomeFlow = MutableSharedFlow<Unit>()
    val navigationToHomeFlow: SharedFlow<Unit> = _navigationToHomeFlow.asSharedFlow()

    private val _navigationToPinVerificationFlow = MutableSharedFlow<Unit>()
    val navigationToPinVerificationFlow: SharedFlow<Unit> = _navigationToPinVerificationFlow.asSharedFlow()

    init {
        getIsPinSet()
    }

    private fun getIsPinSet() = viewModelScope.launch {
        val isPinSet = getIsPinSetUseCase()
        if (isPinSet) {
            _navigationToPinVerificationFlow.emit(Unit)
        } else {
            _navigationToHomeFlow.emit(Unit)
        }
    }
}