package com.antsfamily.naughtynotes.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.repository.SettingsRepository
import com.antsfamily.naughtynotes.ui.theme.AppThemeSwitcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        getSettings()
    }

    private fun getSettings() = viewModelScope.launch {
        try {
            val isDarkMode = repository.getIsDarkMode()
            val pinCode = repository.getPinCode()

            _state.value = SettingsUiState.Content(
                isDarkMode = isDarkMode,
                isAppProtected = pinCode != null
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

    fun onPinClick(isEnabled: Boolean) {
        if (isEnabled) {
            //TODO implement mechanism that deletes PIN set
        } else {
            //TODO implement bottom dialog where you can setup PIN
        }
    }
}