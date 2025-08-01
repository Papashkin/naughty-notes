package com.antsfamily.naughtynotes.presentation.noteform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SexRecordRepository
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import com.antsfamily.naughtynotes.presentation.noteform.model.NoteFormType
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

@HiltViewModel(assistedFactory = NoteFormViewModel.Factory::class)
class NoteFormViewModel @AssistedInject constructor(
    private val repository: SexRecordRepository,
    @Assisted("dateEpoch") private val dateEpoch: Long,
    @Assisted("noteId") private val noteId: Int?,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("dateEpoch") dateEpoch: Long,
            @Assisted("noteId") noteId: Int?
        ): NoteFormViewModel
    }

    private val _state: MutableStateFlow<NoteFormUiState> =
        MutableStateFlow(NoteFormUiState.Loading)
    val state: StateFlow<NoteFormUiState> = _state

    private val _navigateBackEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateBackEvent: SharedFlow<Unit> = _navigateBackEvent

    private val _noteSaveSnackBarEvent: MutableSharedFlow<SexType> = MutableSharedFlow()
    val noteSaveSnackBarEvent: SharedFlow<SexType> = _noteSaveSnackBarEvent

    private val selectedDate: LocalDate by lazy { LocalDate.ofEpochDay(dateEpoch) }

    init {
        if (noteId == null) {
            setupCreateNoteContent()
        } else {
            setupEditNoteContent(noteId)
        }
    }

    fun setPleasureRate(rate: Int) {
        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(rate = rate)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun setPainRate(painRate: Int) {
        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(painRate = painRate)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun setNote(note: String) {
        if (note.length > CREATE_NOTE_NOTE_LENGTH_MAX) return

        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(note = note)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun setSexType(type: SexType) {
        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(type = type)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun setIsProtected(isProtected: Boolean) {
        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(isProtected = isProtected)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        (_state.value as? NoteFormUiState.Content)?.let {
            _state.value = it.copy(isSaveButtonLoadingVisible = true)

            val note = NoteModel(
                id = noteId ?: Random.nextInt(),
                date = it.date,
                type = it.type,
                isProtected = it.isProtected,
                rate = it.rate,
                painRate = it.painRate,
                personalNote = it.note
            )

            noteId?.let {
                updateExistedNote(note)
            } ?: run {
                saveNewNote(note)
            }
        }
    }

    private suspend fun updateExistedNote(note: NoteModel) {
        repository.updateNote(note)
        delay(200) //TODO remove it later, it's just to see loading
        _noteSaveSnackBarEvent.emit(note.type)
        setEditNoteDefaultState(note)
    }

    private suspend fun saveNewNote(note: NoteModel) {
        repository.addNote(note)
        delay(200) //TODO remove it later, it's just to see loading
        _noteSaveSnackBarEvent.emit(note.type)
        setCreateNoteDefaultState()
    }

    private fun checkSaveButtonAvailability() {
        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(isSaveButtonEnabled = it.isValid)
                else -> it
            }
        }
    }

    private fun setCreateNoteDefaultState() {
        _state.value = NoteFormUiState.Content.Default.copy(date = selectedDate)
    }

    private fun setEditNoteDefaultState(note: NoteModel) {
        _state.value = NoteFormUiState.Content(
            formType = NoteFormType.EDIT,
            date = selectedDate,
            type = note.type,
            isProtected = note.isProtected,
            rate = note.rate,
            painRate = note.painRate,
            note = note.personalNote,
            isSaveButtonEnabled = true,
            isSaveButtonLoadingVisible = false
        )
    }

    private fun setupCreateNoteContent() {
        _state.value = NoteFormUiState.Content.Default.copy(date = selectedDate)
    }

    private fun setupEditNoteContent(noteId: Int) = viewModelScope.launch {
        val note = repository.getNoteById(noteId)
        note?.let {
            _state.value = NoteFormUiState.Content(
                formType = NoteFormType.EDIT,
                date = selectedDate,
                type = it.type,
                isProtected = it.isProtected,
                rate = it.rate,
                painRate = it.painRate,
                note = it.personalNote,
                isSaveButtonEnabled = true,
                isSaveButtonLoadingVisible = false
            )
        } ?: run {
            //TODO implement error handling
        }
    }
}