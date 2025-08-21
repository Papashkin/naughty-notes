package com.antsfamily.naughtynotes.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.GetSettingsUseCase
import com.antsfamily.domain.RemovePinCodeUseCase
import com.antsfamily.domain.SetDarkThemeUseCase
import com.antsfamily.naughtynotes.ui.theme.AppThemeSwitcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val removePinCodeUseCase: RemovePinCodeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    private val themeSwitcher: AppThemeSwitcher
) : ViewModel() {

    private val _state = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val state: StateFlow<SettingsUiState>
        get() = _state

    private val _setPinCodeDialogVisibilityEvent = MutableSharedFlow<Boolean>()
    val setPinCodeDialogVisibilityEvent: SharedFlow<Boolean> =
        _setPinCodeDialogVisibilityEvent.asSharedFlow()

    init {
        getSettings()
    }

    private fun getSettings() = viewModelScope.launch {
        try {
            val settings = getSettingsUseCase()

            _state.value = SettingsUiState.Content(
                isDarkMode = settings.isDarkMode,
                isAppProtected = settings.isPinCodeSet,
            )
        } catch (e: Exception) {
            //TODO implement error handling
        }
    }

    fun onThemeChanged(isDarkMode: Boolean) = viewModelScope.launch {
        setDarkThemeUseCase(isDarkMode)
        themeSwitcher.setAppTheme(isDarkMode)
        _state.update {
            when (it) {
                is SettingsUiState.Content -> it.copy(isDarkMode = isDarkMode)
                else -> it
            }
        }
    }

    fun onPinClick(isEnabled: Boolean) = viewModelScope.launch {
        if (isEnabled) {
            _setPinCodeDialogVisibilityEvent.emit(true)
        } else {
            onPinCodeDisabled()
        }
    }

    fun onPinCodeSaved() = viewModelScope.launch {
        _state.update {
            when (it) {
                is SettingsUiState.Content -> it.copy(isAppProtected = true)
                else -> it
            }
        }
        _setPinCodeDialogVisibilityEvent.emit(false)
    }

    private fun onPinCodeDisabled() {
        try {
            removePinCodeUseCase()
            _state.update {
                when (it) {
                    is SettingsUiState.Content -> it.copy(isAppProtected = false)
                    else -> it
                }
            }
        } catch (e: Exception) {
            //TODO implement error handling mechanism
        }

    }
}