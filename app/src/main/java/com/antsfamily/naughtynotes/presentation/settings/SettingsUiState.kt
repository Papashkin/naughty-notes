package com.antsfamily.naughtynotes.presentation.settings

import com.antsfamily.domain.model.ErrorType

sealed class SettingsUiState {
    data object Loading : SettingsUiState()
    data class Error(val type: ErrorType) : SettingsUiState()
    data class Content(
        val isAppProtected: Boolean,
        val isDarkMode: Boolean,
        val appVersion: String?,
    ) : SettingsUiState()
}