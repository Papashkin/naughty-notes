package com.antsfamily.sexcalendar.presentation.createnote

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<CreateNoteUiState> =
        MutableStateFlow(CreateNoteUiState.Loading)
    val state: StateFlow<CreateNoteUiState> = _state

    private val _navigateBackEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackEvent: SharedFlow<Unit> = _navigateBackEvent
}