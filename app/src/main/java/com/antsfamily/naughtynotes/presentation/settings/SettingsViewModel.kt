package com.antsfamily.naughtynotes.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.repository.SettingsRepository
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
    private val repository: SettingsRepository,
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
            val isDarkMode = repository.getIsDarkMode()
            val isPinCodeSet = repository.isPinCodeSet()

            _state.value = SettingsUiState.Content(
                isDarkMode = isDarkMode,
                isAppProtected = isPinCodeSet,
            )
        } catch (e: Exception) {
            //TODO implement error handling
        }
    }

    fun onThemeChanged(isDarkMode: Boolean) = viewModelScope.launch {
        repository.setIsDarkMode(isDarkMode)
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
            repository.removePinCode()
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