package com.antsfamily.naughtynotes.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.repository.NoteRepository
import com.antsfamily.naughtynotes.design.navigation.DURATION_ANIMATION
import com.kizitonwose.calendar.core.yearMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    val navigateToNoteFormEvent: SharedFlow<Long> = _navigateToNoteFormEvent.asSharedFlow()

    private val _navigateToAllNotesEvent: MutableSharedFlow<Long> = MutableSharedFlow()
    val navigateToAllNotesEvent: SharedFlow<Long> = _navigateToAllNotesEvent.asSharedFlow()

    private val _navigateToSettingsEvent: MutableSharedFlow<Unit> = MutableSharedFlow()
    val navigateToSettingsEvent: SharedFlow<Unit> = _navigateToSettingsEvent.asSharedFlow()

    //    private var notes: List<NoteModel> = mutableListOf()
    private var groupedNotes: Map<YearMonth, List<NoteModel>> = mutableMapOf()
    private var isClickEnabled: Boolean = true

    private val currentMonth: YearMonth = YearMonth.now()
    private val today: LocalDate = LocalDate.now()
    private val debounceInterval: Long = 300L

    init {
        getNotes()
    }

    private fun getNotes() = viewModelScope.launch(Dispatchers.Default) {
        delay(DURATION_ANIMATION.toLong())
        repository.notes
            .onStart { _state.value = HomeUiState.Loading }
            .catch { /* no-op */ }
            .collect {
                handleNotes(it)
            }
    }

    private suspend fun handleNotes(notes: List<NoteModel>) {
        groupedNotes = notes.groupBy { it.date.yearMonth }

        val lastNoteDate = notes.maxByOrNull { it.date }?.date ?: today
        val daysSinceLastNote = ChronoUnit
            .DAYS
            .between(lastNoteDate, today)
            .toInt()

        withContext(Dispatchers.Main) {
            _state.value = HomeUiState.Content(
                yearMonth = currentMonth,
                isCurrentMonth = true,
                datesWithNotes = notes.getDatesForMonth(currentMonth),
                daysSinceLastNote = abs(daysSinceLastNote),
            )
        }
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ChangeMonth -> onMonthChanged(intent.yearMonth)
            is HomeIntent.SelectDay -> onDayClick(intent.date)
            is HomeIntent.Settings -> onSettingsClick()
            is HomeIntent.ShowToday -> onTodayButtonClick()
        }
    }

    private fun onMonthChanged(month: YearMonth) = viewModelScope.launch(Dispatchers.Default) {
        if (month == (_state.value as? HomeUiState.Content)?.yearMonth) return@launch
        val properNotes = groupedNotes.getDatesForMonth(month)

        withContext(Dispatchers.Main) {
            _state.update {
                when (it) {
                    is HomeUiState.Content -> it.copy(
                        yearMonth = month,
                        datesWithNotes = properNotes,
                        isCurrentMonth = month == currentMonth
                    )

                    else -> it
                }
            }
        }
    }

    private fun onTodayButtonClick() {
        _state.update {
            when (it) {
                is HomeUiState.Content -> it.copy(
                    yearMonth = currentMonth,
                    datesWithNotes = groupedNotes.getDatesForMonth(currentMonth),
                    isCurrentMonth = true
                )

                else -> it
            }
        }
    }

    private fun onDayClick(date: LocalDate) = viewModelScope.launch {
        if (date.isAfter(today) || !isClickEnabled) return@launch

        isClickEnabled = false

        val notesForDate = groupedNotes[date.yearMonth].orEmpty().filter { it.date == date }

        if (notesForDate.isEmpty()) {
            _navigateToNoteFormEvent.emit(date.toEpochDay())
        } else {
            _navigateToAllNotesEvent.emit(date.toEpochDay())
        }
        delay(debounceInterval)
        isClickEnabled = true
    }

    fun onSettingsClick() = viewModelScope.launch {
        _navigateToSettingsEvent.emit(Unit)
    }

    private fun List<NoteModel>.getDatesForMonth(month: YearMonth): List<LocalDate> =
        this.filter { note -> note.date.yearMonth == month }
            .map { note -> note.date }

    private fun Map<YearMonth, List<NoteModel>>.getDatesForMonth(month: YearMonth): List<LocalDate> =
        this.getOrElse(month) { emptyList() }.map { it.date }
}