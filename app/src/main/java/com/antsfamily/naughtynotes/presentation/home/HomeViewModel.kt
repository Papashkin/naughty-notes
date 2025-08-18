package com.antsfamily.naughtynotes.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.repository.NoteRepository
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.naughtynotes.presentation.util.getDatesForMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val state: StateFlow<HomeUiState>
        get() = _state

    private val _navigateToNoteFormEvent: MutableSharedFlow<Long> = MutableSharedFlow()
    val navigateToNoteFormEvent: SharedFlow<Long>
        get() = _navigateToNoteFormEvent

    private val _navigateToAllNotesEvent: MutableSharedFlow<Long> = MutableSharedFlow()
    val navigateToAllNotesEvent: SharedFlow<Long>
        get() = _navigateToAllNotesEvent

    private var notes: List<NoteModel> = mutableListOf()
    private val currentMonth: YearMonth = YearMonth.now()

    init {
        getNotes()
    }

    private fun getNotes() = viewModelScope.launch {
        repository.notes
            .onStart { /* no-op */ }
            .onCompletion {
                Log.e(
                    this@HomeViewModel::class.simpleName,
                    "=== Notes fetching COMPLETE ==="
                )
            }
            .collect {
                notes = it
                handleNotes()
            }
    }

    private fun handleNotes() {
        val today = LocalDate.now().dayOfMonth
        val lastNoteDate = notes.maxByOrNull { it.date }?.date?.dayOfMonth ?: 0
        _state.value = HomeUiState.Content(
            yearMonth = currentMonth,
            isCurrentMonth = true,
            datesWithNotes = notes.getDatesForMonth(currentMonth),
            daysSinceLastNote = today - lastNoteDate
        )
    }

    fun onMonthChanged(month: YearMonth) {
        if (month == (_state.value as? HomeUiState.Content)?.yearMonth) return

        _state.update {
            when (it) {
                is HomeUiState.Content -> it.copy(
                    yearMonth = month,
                    datesWithNotes = notes.getDatesForMonth(month),
                    isCurrentMonth = month == currentMonth
                )

                else -> it
            }
        }
    }

    fun onTodayButtonClick() {
        _state.update {
            when (it) {
                is HomeUiState.Content -> it.copy(
                    yearMonth = currentMonth,
                    datesWithNotes = notes.getDatesForMonth(currentMonth),
                    isCurrentMonth = true
                )

                else -> it
            }
        }
    }

    fun onDayClick(date: LocalDate) = viewModelScope.launch {
        val currentDate = LocalDate.now()
        if (date.isAfter(currentDate)) return@launch

        val notesForDate = notes.filter { it.date == date }
        if (notesForDate.isEmpty()) {
            _navigateToNoteFormEvent.emit(date.toEpochDay())
        } else {
            _navigateToAllNotesEvent.emit(date.toEpochDay())
        }
    }

    fun onSettingsClick() {
        //TODO implement Settings screen
    }
}