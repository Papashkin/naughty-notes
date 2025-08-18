package com.antsfamily.naughtynotes.presentation.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): ViewModel() {
    //TODO implement Settings logic here

    private val _state = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val state: StateFlow<SettingsUiState>
        get() = _state

    init {
        _state.value = SettingsUiState.Content(false, true)
    }

    fun onPinClick(isSelected: Boolean) {
        if (isSelected) {
            //TODO implement mechanism that deletes PIN set
        } else {
            //TODO implement bottom dialog where you can setup PIN
        }
    }
}