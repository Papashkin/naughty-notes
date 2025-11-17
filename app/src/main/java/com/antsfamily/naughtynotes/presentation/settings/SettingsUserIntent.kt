package com.antsfamily.naughtynotes.presentation.settings

sealed class SettingsIntent {
    data object OpenStatistics: SettingsIntent()
    data class SetPin(val isEnabled: Boolean): SettingsIntent()
    data object ChangePin: SettingsIntent()
    data class SwitchTheme(val isDarkMode: Boolean): SettingsIntent()
}