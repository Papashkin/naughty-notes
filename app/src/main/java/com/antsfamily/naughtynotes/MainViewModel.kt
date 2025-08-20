package com.antsfamily.naughtynotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.repository.SettingsRepository
import com.antsfamily.naughtynotes.ui.theme.AppThemeSwitcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    repository: SettingsRepository,
    private val themeSwitcher: AppThemeSwitcher,
) : ViewModel() {

    private val _state = MutableStateFlow(repository.getIsDarkMode())
    val state: StateFlow<Boolean> = _state

    init {
        subscribeToDarkThemeChange()
    }

    private fun subscribeToDarkThemeChange() = viewModelScope.launch {
        themeSwitcher.darkThemeState.collectLatest { isDarkTheme ->
            _state.value = isDarkTheme
        }
    }
}
