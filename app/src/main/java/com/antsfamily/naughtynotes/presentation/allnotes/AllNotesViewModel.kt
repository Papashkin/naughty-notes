package com.antsfamily.naughtynotes.presentation.allnotes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SexRecordRepository
import com.antsfamily.domain.model.NoteModel
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
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

@HiltViewModel(assistedFactory = AllNotesViewModel.Factory::class)
class AllNotesViewModel @AssistedInject constructor(
    private val repository: SexRecordRepository,
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

    private val _navigationToCreateNoteFlow = MutableSharedFlow<Unit>()
    val navigationToCreateNoteFlow: SharedFlow<Unit> = _navigationToCreateNoteFlow.asSharedFlow()

    private val _deleteNoteFlow = MutableSharedFlow<NoteModel>()
    val deleteNoteFlow: SharedFlow<NoteModel> = _deleteNoteFlow.asSharedFlow()

    private var noteToDelete: NoteModel? = null

    init {
        getNotes()
    }

    fun onEditSwipe(note: NoteModel) {
        //TODO implement Edit Note screen
    }

    fun onDeleteSwipe(note: NoteModel) = viewModelScope.launch {
        noteToDelete = note
        repository.deleteNote(note)
        _state.update {
            when (it) {
                is AllNotesUiState.Content -> it.copy(notes = it.notes.minus(note))
                else -> it
            }
        }
        _deleteNoteFlow.emit(note)
    }

    fun onDeleteNoteReverted() = viewModelScope.launch {
        noteToDelete?.let { note ->
            repository.addNote(note)
            _state.update { state ->
                when (state) {
                    is AllNotesUiState.Content -> state.copy(
                        notes = state.notes.plus(note).sortedBy { it.date })

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
        _navigationToCreateNoteFlow.emit(Unit)
    }

    private fun getNotes() = viewModelScope.launch {
        val date = LocalDate.ofEpochDay(epoch)
        try {
            val notes = repository.getNotesByDate(date)
            _state.value = AllNotesUiState.Content(date, notes)
        } catch (e: Exception) {
            //TODO fix it later with error Type and it's handler
            _state.value = AllNotesUiState.Error(e.message.orEmpty())
        } finally {
            subscribeToNotes(date)
        }
    }

    private suspend fun subscribeToNotes(date: LocalDate) {
        repository.subscribeToNotesOnDate(date)
            .onStart { /* no-op */ }
            .onCompletion {
                Log.e(
                    this@AllNotesViewModel::class.simpleName, "=== Notes fetching COMPLETE ==="
                )
            }
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
}