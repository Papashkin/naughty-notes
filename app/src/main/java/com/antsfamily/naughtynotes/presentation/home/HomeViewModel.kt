package com.antsfamily.naughtynotes.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SexRecordRepository
import com.antsfamily.domain.model.NoteModel
import com.kizitonwose.calendar.core.yearMonth
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
import java.time.Month
import java.time.Year
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: SexRecordRepository
) : ViewModel() {

    companion object {
        private val LAST_AVAILABLE_YEAR = Year.now().value.minus(1)
    }

    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val state: StateFlow<HomeUiState>
        get() = _state

    private val _navigateToCreateNoteEvent: MutableSharedFlow<Long> = MutableSharedFlow()
    val navigateToCreateNoteEvent: SharedFlow<Long>
        get() = _navigateToCreateNoteEvent

    private val _navigateToAllNotesEvent: MutableSharedFlow<Long> = MutableSharedFlow()
    val navigateToAllNotesEvent: SharedFlow<Long>
        get() = _navigateToAllNotesEvent

    private var notes: List<NoteModel> = mutableListOf()
    private val yearMonthNow: YearMonth = YearMonth.now()

    init {
        getNotes()
    }

    fun onPreviousMonthClick() {
        val currentYearMonth = (_state.value as HomeUiState.Content).yearMonth
        val selectedYearMonth = currentYearMonth.minusMonths(1)

        val isNavigationBackInvisible =
            selectedYearMonth.year == LAST_AVAILABLE_YEAR && selectedYearMonth.month == Month.JANUARY

        _state.update {
            when (it) {
                is HomeUiState.Content -> it.copy(
                    yearMonth = selectedYearMonth,
                    isCurrentMonth = selectedYearMonth.monthValue == yearMonthNow.monthValue,
                    datesWithNotes = notes.filter { note -> note.date.yearMonth == selectedYearMonth }
                        .map { note -> note.date },
                    isNavigationBackVisible = !isNavigationBackInvisible,
                    isNavigationForwardVisible = selectedYearMonth != yearMonthNow
                )

                else -> it
            }
        }
    }

    fun onNextMonthClick() {
        val currentYearMonth = (_state.value as HomeUiState.Content).yearMonth
        val selectedYearMonth = currentYearMonth.plusMonths(1)

        val isNavigationBackVisible =
            selectedYearMonth.year != LAST_AVAILABLE_YEAR && selectedYearMonth.month != Month.JANUARY

        _state.update {
            when (it) {
                is HomeUiState.Content -> it.copy(
                    yearMonth = selectedYearMonth,
                    isCurrentMonth = selectedYearMonth.monthValue == yearMonthNow.monthValue,
                    datesWithNotes = notes.filter { note -> note.date.yearMonth == selectedYearMonth }
                        .map { note -> note.date },
                    isNavigationBackVisible = isNavigationBackVisible,
                    isNavigationForwardVisible = selectedYearMonth != yearMonthNow
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
            _navigateToCreateNoteEvent.emit(date.toEpochDay())
        } else {
            _navigateToAllNotesEvent.emit(date.toEpochDay())
        }
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
            yearMonth = yearMonthNow,
            isCurrentMonth = true,
            isNavigationBackVisible = true,
            isNavigationForwardVisible = false,
            datesWithNotes = notes.filter { it.date.yearMonth == yearMonthNow }.map { it.date },
            daysSinceLastNote = today - lastNoteDate
        )
    }
}