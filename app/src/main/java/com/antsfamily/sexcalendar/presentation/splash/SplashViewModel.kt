package com.antsfamily.sexcalendar.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel() {

    private val _navigationToHomeFlow = MutableSharedFlow<Unit>()
    val navigationToHomeFlow: SharedFlow<Unit> = _navigationToHomeFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(1500)
            _navigationToHomeFlow.emit(Unit)
        }
    }
}