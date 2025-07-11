package com.antsfamily.sexcalendar.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Month
import java.time.Year
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    companion object {
        private val LAST_AVAILABLE_YEAR = Year.now().value.minus(2)
    }

    private val _state: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val state: StateFlow<HomeUiState>
        get() = _state

    private val _navigateToCreateNoteEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateToCreateNoteEvent: SharedFlow<Unit>
        get() = _navigateToCreateNoteEvent

    private val yearMonthNow: YearMonth = YearMonth.now()

    init {
        _state.value = HomeUiState.Content(
            yearMonth = yearMonthNow,
            isNavigationBackVisible = true,
            isNavigationForwardVisible = false
        )
    }

    fun onPreviousMonthClick() {
        val currentYearMonth = (_state.value as HomeUiState.Content).yearMonth
        val selectedYearMonth = currentYearMonth.minusMonths(1)

        val isNavigationBackInvisible =
            selectedYearMonth.year == LAST_AVAILABLE_YEAR && selectedYearMonth.month == Month.JANUARY

        _state.value = HomeUiState.Content(
            yearMonth = selectedYearMonth,
            isNavigationBackVisible = !isNavigationBackInvisible,
            isNavigationForwardVisible = selectedYearMonth != yearMonthNow
        )
    }

    fun onNextMonthClick() {
        val currentYearMonth = (_state.value as HomeUiState.Content).yearMonth
        val selectedYearMonth = currentYearMonth.plusMonths(1)

        val isNavigationBackVisible =
            selectedYearMonth.year != LAST_AVAILABLE_YEAR && selectedYearMonth.month != Month.JANUARY

        _state.value = HomeUiState.Content(
            yearMonth = selectedYearMonth,
            isNavigationBackVisible = isNavigationBackVisible,
            isNavigationForwardVisible = selectedYearMonth != yearMonthNow
        )
    }

    fun onCreateNoteClick() = viewModelScope.launch {
        _navigateToCreateNoteEvent.emit(Unit)
    }
}