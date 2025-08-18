package com.antsfamily.naughtynotes.presentation.settings

sealed class SettingsUiState {
    data object Loading: SettingsUiState()
    data class Content(
        val isAppProtected: Boolean,
        val isDarkMode: Boolean
    ): SettingsUiState()
}