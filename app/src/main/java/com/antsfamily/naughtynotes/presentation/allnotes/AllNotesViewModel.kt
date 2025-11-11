package com.antsfamily.naughtynotes.presentation.allnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.AddNoteUseCase
import com.antsfamily.domain.DeleteNoteUseCase
import com.antsfamily.domain.GetNotesByDateUseCase
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.model.toType
import com.antsfamily.domain.repository.NoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel(assistedFactory = AllNotesViewModel.Factory::class)
class AllNotesViewModel @AssistedInject constructor(
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val getNotesByDateUseCase: GetNotesByDateUseCase,
    private val repository: NoteRepository,
    @Assisted("epoch") private val epoch: Long,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("epoch") epoch: Long,
        ): AllNotesViewModel
    }

    private val _state = MutableStateFlow<AllNotesUiState>(AllNotesUiState.Loading)
    val state: StateFlow<AllNotesUiState> = _state.asStateFlow()

    private val _navigationBackFlow = MutableSharedFlow<Unit>()
    val navigationBackFlow: SharedFlow<Unit> = _navigationBackFlow.asSharedFlow()

    private val _navigationToNoteFormFlow = MutableSharedFlow<Int?>()
    val navigationToNoteFormFlow: SharedFlow<Int?> = _navigationToNoteFormFlow.asSharedFlow()

    private val _deleteNoteFlow = MutableSharedFlow<Unit>()
    val deleteNoteFlow: SharedFlow<Unit> = _deleteNoteFlow.asSharedFlow()

    private var noteToDelete: NoteModel? = null

    init {
        getNotes()
    }

    private fun getNotes() = viewModelScope.launch {
        val date = LocalDate.ofEpochDay(epoch)
        val result = getNotesByDateUseCase(date)

        when (result) {
            is UseCaseResult.Success -> handleNotesByDateSuccessResult(result.data, date)
            is UseCaseResult.Error -> handleNotesByDateErrorResult(result.exception)
        }
    }

    private suspend fun handleNotesByDateSuccessResult(notes: List<NoteModel>, date: LocalDate) {
        _state.value = if (notes.isNotEmpty()) {
            AllNotesUiState.Content(notes)
        } else {
            AllNotesUiState.EmptyContent
        }
        subscribeToNotes(date)
    }

    private fun handleNotesByDateErrorResult(e: Exception) {
        _state.value = AllNotesUiState.Error(e.toType())
    }

    private suspend fun subscribeToNotes(date: LocalDate) {
        repository.subscribeToNotesOnDate(date)
            .catch { /* no-op */ }
            .collect {
                onNewNotesReceived(it)
            }
    }

    private fun onNewNotesReceived(notes: List<NoteModel>) {
        _state.update {
            if (it !is AllNotesUiState.Content) return@update it

            if (it.notes == notes) it else it.copy(notes = notes)
        }
    }

    fun onEditClick(note: NoteModel) = viewModelScope.launch {
        _navigationToNoteFormFlow.emit(note.id)
    }

    fun onDeleteClick(note: NoteModel) = viewModelScope.launch {
        noteToDelete = note
        deleteNoteUseCase(note)

        (_state.value as? AllNotesUiState.Content)?.let { state ->
            val updatedNotesList = state.notes.minus(note)
            if (updatedNotesList.isNotEmpty()) {
                _state.update { state.copy(notes = updatedNotesList) }
            } else {
                _state.update { AllNotesUiState.EmptyContent }
            }
        }

        _deleteNoteFlow.emit(Unit)
    }

    fun onDeleteNoteReverted() = viewModelScope.launch {
        noteToDelete?.let { note ->
            addNoteUseCase(note)
            _state.update { state ->
                when (state) {
                    is AllNotesUiState.Content -> state.copy(
                        notes = state.notes.plus(note).sortedBy { it.date }
                    )

                    is AllNotesUiState.EmptyContent -> AllNotesUiState.Content(listOf(note))
                    else -> state
                }
            }
        }
        noteToDelete = null
    }

    fun onDeleteNoteSuccess() {
        noteToDelete = null
    }

    fun onAddNoteClick() = viewModelScope.launch {
        _navigationToNoteFormFlow.emit(null)
    }

    fun onRetryClick() {
        _state.value = AllNotesUiState.Loading
        getNotes()
    }
}