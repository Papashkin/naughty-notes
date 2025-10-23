package com.antsfamily.naughtynotes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.repository.NoteRepository
import com.kizitonwose.calendar.core.yearMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.abs

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

    private val _navigateToProfileEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateToProfileEvent: SharedFlow<Unit>
        get() = _navigateToProfileEvent

    private var notes: List<NoteModel> = mutableListOf()
    private val currentMonth: YearMonth = YearMonth.now()
    private val today: LocalDate = LocalDate.now()

    init {
        getNotes()
    }

    private fun getNotes() = viewModelScope.launch {
        repository.notes
            .onStart { _state.value = HomeUiState.Loading }
            .catch { /* no-op */ }
            .collect {
                handleNotes(it)
            }
    }

    private fun handleNotes(notes: List<NoteModel>) {
        this.notes = notes
        val lastNoteDate = notes.maxByOrNull { it.date }?.date ?: today
        val daysSinceLastNote = ChronoUnit
            .DAYS
            .between( lastNoteDate, today)
            .toInt()
        _state.value = HomeUiState.Content(
            yearMonth = currentMonth,
            isCurrentMonth = true,
            datesWithNotes = notes.getDatesForMonth(currentMonth),
            daysSinceLastNote = abs(daysSinceLastNote)
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
        if (date.isAfter(today)) return@launch

        val notesForDate = notes.filter { it.date == date }
        if (notesForDate.isEmpty()) {
            _navigateToNoteFormEvent.emit(date.toEpochDay())
        } else {
            _navigateToAllNotesEvent.emit(date.toEpochDay())
        }
    }

    fun onProfileClick() = viewModelScope.launch {
        _navigateToProfileEvent.emit(Unit)
    }

    private fun List<NoteModel>.getDatesForMonth(month: YearMonth): List<LocalDate> =
        this.filter { note -> note.date.yearMonth == month }
            .map { note -> note.date }
}