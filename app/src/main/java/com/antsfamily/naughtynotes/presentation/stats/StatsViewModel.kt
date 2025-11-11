package com.antsfamily.naughtynotes.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.toType
import com.antsfamily.domain.repository.NoteRepository
import com.antsfamily.naughtynotes.presentation.stats.model.CHIP_TYPE_DEFAULT
import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.model.TIMEFRAME_DEFAULT
import com.antsfamily.naughtynotes.presentation.stats.model.TimeFrameItem
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.core.yearMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: NoteRepository,
) : ViewModel() {

    companion object {
        private val currentDate = LocalDate.now()
        private const val DELAY_SHORT = 100L
    }

    private val _state: MutableStateFlow<StatsUiState> = MutableStateFlow(StatsUiState.Loading)
    val state: StateFlow<StatsUiState> get() = _state

    private var selectorType: StatChipType = CHIP_TYPE_DEFAULT
    private var selectorTimeframe: TimeFrameItem = TIMEFRAME_DEFAULT

    private lateinit var _allNotes: List<NoteModel>

    init {
        getNotes()
    }

    private fun getNotes() = viewModelScope.launch {
        try {
            val notes = repository.getAllNotes()
            handleAllNotesSuccessResult(notes)
        } catch (e: Exception) {
            handleAllNotesErrorResult(e)
        }
    }

    private fun handleAllNotesErrorResult(e: Exception) {
        _state.value = StatsUiState.Error(e.toType())
    }

    private fun handleAllNotesSuccessResult(notes: List<NoteModel>) {
        _allNotes = notes
        setStatContent()
    }

    private fun setStatContent() = viewModelScope.launch {
        val statItems = getStats(selectorType, selectorTimeframe)
        _state.value = StatsUiState.Content(statItems)
    }

    private suspend fun getStats(
        chipType: StatChipType,
        timeframe: TimeFrameItem
    ) = withContext(Dispatchers.IO) {
        val notesByTimeframe = when (timeframe) {
            TimeFrameItem.CURRENT_MONTH -> {
                _allNotes.filter { it.date.month == currentDate.month }
            }

            TimeFrameItem.PREV_MONTH -> {
                val previousMonth = YearMonth.now().previousMonth
                _allNotes.filter { it.date.month == previousMonth.month }
            }

            TimeFrameItem.THIS_YEAR -> {
                _allNotes.filter { it.date.yearMonth == YearMonth.now() }
            }

            TimeFrameItem.ALL_TIME -> _allNotes
        }

        val statItems = notesByTimeframe
            .groupBy {
                when (chipType) {
                    StatChipType.ACTIVITY -> it.type
                    StatChipType.PLACE -> it.location
                }
            }
            .toList()
            .sortedByDescending { (_, notes) -> notes.size }
            .map { (type, notes) -> StatsItem(info = type, value = notes.size) }

        statItems
    }

    fun onChipChange(type: StatChipType) = viewModelScope.launch {
        _state.value = StatsUiState.Loading
        selectorType = type
        delay(DELAY_SHORT)
        setStatContent()
    }

    fun onTimeframeChange(timeframe: TimeFrameItem) = viewModelScope.launch {
        _state.value = StatsUiState.Loading
        selectorTimeframe = timeframe
        delay(DELAY_SHORT)
        setStatContent()
    }
}