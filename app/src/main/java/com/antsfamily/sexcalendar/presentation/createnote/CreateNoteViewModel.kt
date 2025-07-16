package com.antsfamily.sexcalendar.presentation.createnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SexRecordRepository
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(
    private val repository: SexRecordRepository
) : ViewModel() {

    private val _state: MutableStateFlow<CreateNoteUiState> =
        MutableStateFlow(CreateNoteUiState.Loading)
    val state: StateFlow<CreateNoteUiState> = _state

    private val _navigateBackEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackEvent: SharedFlow<Unit> = _navigateBackEvent

    init {
        _state.value = CreateNoteUiState.Content.Default
    }

    fun setPleasureRate(rate: Int) {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(rate = rate)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun setPainRate(painRate: Int) {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(painRate = painRate)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun setNote(note: String) {
        if (note.length > 60) return

        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(note = note)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun setSexType(type: SexType) {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(type = type)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun setIsProtected(isProtected: Boolean) {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(isProtected = isProtected)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun onSaveButtonClicked() = viewModelScope.launch {
        (_state.value as? CreateNoteUiState.Content)?.let {
            _state.value = it.copy(isSaveButtonLoadingVisible = true)

            val note = NoteModel(
                date = LocalDate.now(),
                type = it.type,
                isProtected = it.isProtected,
                rate = it.rate,
                painRate = it.painRate,
                personalNote = it.note
            )

            repository.saveData(note)

            _state.value = CreateNoteUiState.Content.Default
        }
    }

    private fun checkSaveButtonAvailability() {
        _state.update {
            when (it) {
                is CreateNoteUiState.Content -> it.copy(isSaveButtonEnabled = it.isValid)
                else -> it
            }
        }
    }
}