package com.antsfamily.sexcalendar.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SexRecordRepository
import com.antsfamily.domain.model.NoteModel
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
        private val LAST_AVAILABLE_YEAR = Year.now().value.minus(2)
    }

    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val state: StateFlow<HomeUiState>
        get() = _state

    private val _navigateToCreateNoteEvent: MutableSharedFlow<Long> = MutableSharedFlow()
    val navigateToCreateNoteEvent: SharedFlow<Long>
        get() = _navigateToCreateNoteEvent

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
                    isNavigationBackVisible = isNavigationBackVisible,
                    isNavigationForwardVisible = selectedYearMonth != yearMonthNow
                )
                else -> it
            }
        }
    }

    fun onCreateNoteClick() = viewModelScope.launch {
        val currentDate = LocalDate.now()
        _navigateToCreateNoteEvent.emit(currentDate.toEpochDay())
    }

    fun onCreateNoteClick(date: LocalDate) = viewModelScope.launch {
        _navigateToCreateNoteEvent.emit(date.toEpochDay())
    }

    private fun getNotes() = viewModelScope.launch {
        repository.notes
            .onStart { /* no-op */ }
            .onCompletion { Log.e("WorkoutsRepo", "!!!! COMPLETE !!!!") }
            .collect { handleNotes(it) }
    }

    private fun handleNotes(notes: List<NoteModel>) {
        _state.value = HomeUiState.Content(
            yearMonth = yearMonthNow,
            isNavigationBackVisible = true,
            isNavigationForwardVisible = false,
            notes = notes
        )
    }
}