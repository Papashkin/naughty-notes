package com.antsfamily.sexcalendar.presentation.createnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SexRecordRepository
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

@HiltViewModel(assistedFactory = CreateNoteViewModel.Factory::class)
class CreateNoteViewModel @AssistedInject constructor(
    private val repository: SexRecordRepository,
    @Assisted("dateEpoch") private val dateEpoch: Long,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("dateEpoch") dateEpoch: Long): CreateNoteViewModel
    }

    private val _state: MutableStateFlow<CreateNoteUiState> =
        MutableStateFlow(CreateNoteUiState.Loading)
    val state: StateFlow<CreateNoteUiState> = _state

    private val _navigateBackEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackEvent: SharedFlow<Unit> = _navigateBackEvent

    private val _noteSaveSnackBarEvent: MutableSharedFlow<SexType> = MutableSharedFlow()
    val noteSaveSnackBarEvent: SharedFlow<SexType> = _noteSaveSnackBarEvent

    init {
        val date = LocalDate.ofEpochDay(dateEpoch)
        _state.value = CreateNoteUiState.Content.Default.copy(date = date)
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
        if (note.length > CREATE_NOTE_NOTE_LENGTH_MAX) return

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
                id = Random.nextInt(),
                date = it.date,
                type = it.type,
                isProtected = it.isProtected,
                rate = it.rate,
                painRate = it.painRate,
                personalNote = it.note
            )

            repository.addNote(note)
            _noteSaveSnackBarEvent.emit(note.type)
            setDefaultState()
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

    private fun setDefaultState() {
        val date = LocalDate.ofEpochDay(dateEpoch)
        _state.value = CreateNoteUiState.Content.Default.copy(date = date)
    }
}