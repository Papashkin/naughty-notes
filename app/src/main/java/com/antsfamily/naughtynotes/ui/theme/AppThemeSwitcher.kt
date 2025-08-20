package com.antsfamily.naughtynotes.ui.theme

import com.antsfamily.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppThemeSwitcher @Inject constructor(
    repository: SettingsRepository
) {

    private val _darkThemeState = MutableStateFlow(repository.getIsDarkMode())
    val darkThemeState: StateFlow<Boolean> = _darkThemeState

    suspend fun setAppTheme(isDark: Boolean) {
        _darkThemeState.emit(isDark)
    }
}
