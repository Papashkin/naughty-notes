package com.antsfamily.sexcalendar.presentation.createnote

import androidx.lifecycle.ViewModel
import com.antsfamily.sexcalendar.presentation.createnote.model.SexType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<CreateNoteUiState> =
        MutableStateFlow(CreateNoteUiState.Loading)
    val state: StateFlow<CreateNoteUiState> = _state

    private val _navigateBackEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackEvent: SharedFlow<Unit> = _navigateBackEvent

    init {
        _state.value =  CreateNoteUiState.Content.Default
    }

    fun setPleasureRate(rate: Int) {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(rate = rate)
                else -> it
            }
        }
    }

    fun setPainRate(painRate: Int) {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(painRate = painRate)
                else -> it
            }
        }
    }

    fun setNote(note: String) {
        if (note.length > 60) return

        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(note = note)
                else -> it
            }
        }
    }

    fun setSexType(type: SexType) {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(type = type)
                else -> it
            }
        }
    }

    fun setIsProtected(isProtected: Boolean) {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(isProtected = isProtected)
                else -> it
            }
        }
    }

    fun onSaveButtonClicked() {
        (_state.value as? CreateNoteUiState.Content)?.let {
            //TODO implement saving mechanism
        }
    }
}