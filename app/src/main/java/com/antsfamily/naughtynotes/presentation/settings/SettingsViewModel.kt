package com.antsfamily.naughtynotes.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.GetSettingsUseCase
import com.antsfamily.domain.RemovePinCodeUseCase
import com.antsfamily.domain.SetDarkThemeUseCase
import com.antsfamily.domain.model.SettingsModel
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.model.toType
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
    private val themeSwitcher: AppThemeSwitcher,
) : ViewModel() {

    private val _state = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val state: StateFlow<SettingsUiState>
        get() = _state

    private val _setPinCodeDialogVisibilityEvent = MutableSharedFlow<Boolean>()
    val setPinCodeDialogVisibilityEvent: SharedFlow<Boolean> =
        _setPinCodeDialogVisibilityEvent.asSharedFlow()

    private val _navigateToStatisticsEvent = MutableSharedFlow<Unit>()
    val navigateToStatisticsEvent: SharedFlow<Unit> = _navigateToStatisticsEvent.asSharedFlow()

    private val _navigateToChangePinEvent = MutableSharedFlow<Unit>()
    val navigateToChangePinEvent: SharedFlow<Unit> = _navigateToChangePinEvent.asSharedFlow()

    init {
        getSettings()
    }

    private fun getSettings() = viewModelScope.launch {
        val result = getSettingsUseCase()
        when (result) {
            is UseCaseResult.Success -> handleGetSettingsSuccessResult(result.data)
            is UseCaseResult.Error -> handleGetSettingsErrorResult(result.exception)
        }
    }

    private fun handleGetSettingsSuccessResult(settings: SettingsModel) {
        _state.value = SettingsUiState.Content(
            isDarkMode = settings.isDarkMode,
            isAppProtected = settings.isPinCodeSet,
            appVersion = settings.appVersion
        )
    }

    private fun handleGetSettingsErrorResult(e: Exception) {
        _state.value = SettingsUiState.Error(e.toType())
    }

    fun handleIntent(intent: SettingsIntent) = viewModelScope.launch {
        when (intent) {
            is SettingsIntent.OpenStatistics -> navigateToStatistics()
            is SettingsIntent.ChangePin -> navigateToChangePin()
            is SettingsIntent.SwitchTheme -> onThemeChanged(intent.isDarkMode)
            is SettingsIntent.SetPin -> onPinClick(intent.isEnabled)
        }
    }

    private suspend fun navigateToStatistics() {
        _navigateToStatisticsEvent.emit(Unit)
    }

    private suspend fun navigateToChangePin() {
        _navigateToChangePinEvent.emit(Unit)
    }

    private suspend fun onThemeChanged(isDarkMode: Boolean) {
        setDarkThemeUseCase(isDarkMode)
        themeSwitcher.setAppTheme(isDarkMode)
        _state.update {
            when (it) {
                is SettingsUiState.Content -> it.copy(isDarkMode = isDarkMode)
                else -> it
            }
        }
    }

    private suspend fun onPinClick(isEnabled: Boolean) {
        if (isEnabled) {
            _setPinCodeDialogVisibilityEvent.emit(true)
        } else {
            onPinCodeDisabled()
        }
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
            Log.e(this::class.simpleName, e.message ?: e.toString())
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
}