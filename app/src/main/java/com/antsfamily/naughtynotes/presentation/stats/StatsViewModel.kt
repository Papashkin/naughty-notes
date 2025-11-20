package com.antsfamily.naughtynotes.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.Other
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: NoteRepository,
) : ViewModel() {

    companion object {
        private val currentDate = LocalDate.now()
        private const val DELAY_SHORT = 20L
        private const val TOP_NOTES_AMOUNT = 3
        private const val PERCENTAGE_100 = 100.0
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
            delay(DELAY_SHORT)
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
        setStatContent(true)
    }

    private fun setStatContent(isInitial: Boolean = false) = viewModelScope.launch {
        val statItems = getStats(selectorType, selectorTimeframe)
        if (isInitial) {
            _state.value = StatsUiState.Content(statItems, getTrends())
        } else {
            _state.update {
                when (it) {
                    is StatsUiState.Content -> it.copy(statItems = statItems)
                    else -> it
                }
            }
        }
    }

    private fun getTrends(): List<Float> {

        val notesByMonth: Map<YearMonth, Int> = _allNotes
            .groupBy { YearMonth.from(it.date) }
            .mapValues { (_, list) -> list.size }

        val lastYearByMonths = (0 until 12).map {
            currentDate.yearMonth.minusMonths(it.toLong())
        }.reversed()

        val trends: List<Float> = lastYearByMonths.map { month ->
            notesByMonth.getOrElse(month) { 0 }.toFloat()
        }

        return trends
    }

    private suspend fun getStats(
        chipType: StatChipType,
        timeframe: TimeFrameItem
    ) = withContext(Dispatchers.IO) {
        val notesByTimeframe = filterNotesByTimeFrameItem(timeframe)
        val allItems = notesByTimeframe.size

        val sortedNotes = notesByTimeframe
            .groupBy {
                when (chipType) {
                    StatChipType.ACTIVITY -> it.type
                    StatChipType.PLACE -> it.location
                }
            }
            .toList()
            .sortedByDescending { (_, notes) -> notes.size }

        if (sortedNotes.size == TOP_NOTES_AMOUNT + 1) {
            return@withContext sortedNotes
                .map { (type, notes) ->
                    StatsItem(
                        info = type,
                        value = notes.size,
                        percent = BigDecimal
                            .valueOf(PERCENTAGE_100 * notes.size / allItems)
                            .setScale(1, RoundingMode.HALF_UP)
                    )
                }
                .sortedByDescending { it.percent }
        }

        val topNotes = sortedNotes.take(TOP_NOTES_AMOUNT)
        val otherNotes = sortedNotes.drop(TOP_NOTES_AMOUNT)
        val otherNotesSize = otherNotes.sumOf { (_, notes) -> notes.size }

        val statItems = topNotes
            .map { (type, notes) ->
                StatsItem(
                    info = type,
                    value = notes.size,
                    percent = BigDecimal
                        .valueOf(PERCENTAGE_100 * notes.size / allItems)
                        .setScale(1, RoundingMode.HALF_UP)
                )
            }

        if (otherNotes.isNotEmpty()) {
            statItems + StatsItem(
                info = Other,
                value = otherNotesSize,
                percent = BigDecimal
                    .valueOf(PERCENTAGE_100 * otherNotesSize / allItems)
                    .setScale(1, RoundingMode.HALF_UP)
            )
        } else {
            statItems
        }
    }

    private fun filterNotesByTimeFrameItem(timeframe: TimeFrameItem): List<NoteModel> {
        return when (timeframe) {
            TimeFrameItem.CURRENT_MONTH -> {
                _allNotes.filter { it.date.month == currentDate.month }
            }

            TimeFrameItem.PREV_MONTH -> {
                val previousMonth = YearMonth.now().previousMonth
                _allNotes.filter { it.date.month == previousMonth.month }
            }

            TimeFrameItem.THIS_YEAR -> {
                _allNotes.filter { it.date.year == currentDate.year }
            }

            TimeFrameItem.ALL_TIME -> _allNotes
        }
    }

    fun onIntentCreated(intent: StatsIntent) = viewModelScope.launch {
        when (intent) {
            is StatsIntent.ShowByTimeframe -> onTimeframeChanged(intent.timeFrameItem)
            is StatsIntent.ShowByType -> onTypeChanged(intent.type)
        }
    }

    private fun onTypeChanged(type: StatChipType) {
        selectorType = type
        setStatContent()
    }

    private fun onTimeframeChanged(timeframe: TimeFrameItem) {
        selectorTimeframe = timeframe
        setStatContent()
    }
}