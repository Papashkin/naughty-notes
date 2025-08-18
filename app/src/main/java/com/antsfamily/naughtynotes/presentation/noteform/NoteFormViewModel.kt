package com.antsfamily.naughtynotes.presentation.noteform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.GetNoteByIdUseCase
import com.antsfamily.domain.SaveOrUpdateNoteUseCase
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.noteform.model.NoteFormType
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

@HiltViewModel(assistedFactory = NoteFormViewModel.Factory::class)
class NoteFormViewModel @AssistedInject constructor(
    private val saveOrUpdateNoteUseCase: SaveOrUpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
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

    private val _noteSaveSnackBarEvent: MutableSharedFlow<PracticeType> = MutableSharedFlow()
    val noteSaveSnackBarEvent: SharedFlow<PracticeType> = _noteSaveSnackBarEvent

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
                is NoteFormUiState.Content -> it.copy(pleasureRate = rate)
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

    fun setPractice(type: PracticeType) {
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

    fun setHasOrgasm(hasOrgasm: Boolean) {
        _state.update {
            when (it) {
                is NoteFormUiState.Content -> it.copy(hasOrgasm = hasOrgasm)
                else -> it
            }
        }
        checkSaveButtonAvailability()
    }

    fun onSaveButtonClick() = viewModelScope.launch {
        (_state.value as? NoteFormUiState.Content)?.let { state ->
            _state.value = state.copy(isSaveButtonLoadingVisible = true)

            saveOrUpdateNoteUseCase.invoke(
                id = noteId,
                date = state.date,
                type = state.type,
                isProtected = state.isProtected,
                hasOrgasm = state.hasOrgasm,
                pleasureRate = state.pleasureRate,
                painRate = state.painRate,
                personalNote = state.note
            )

            _noteSaveSnackBarEvent.emit(state.type)

            if (noteId == null) {
                setCreateNoteDefaultState()
            }
        }
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

    private fun setupCreateNoteContent() {
        _state.value = NoteFormUiState.Content.Default.copy(date = selectedDate)
    }

    private fun setupEditNoteContent(noteId: Int) = viewModelScope.launch {
        val note = getNoteByIdUseCase(noteId)
        note?.let {
            _state.value = NoteFormUiState.Content(
                formType = NoteFormType.EDIT,
                date = selectedDate,
                type = it.type,
                isProtected = it.isProtected,
                hasOrgasm = it.hasOrgasm,
                pleasureRate = it.rate,
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