package com.antsfamily.naughtynotes.presentation.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.domain.model.toType
import com.antsfamily.domain.repository.NoteRepository
import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
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
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: NoteRepository,
) : ViewModel() {

    companion object {
        private const val STATS_INIT_DELAY = 200L
        private const val TOP_NOTES_AMOUNT = 3
        private const val PERCENTAGE_100 = 100.0
    }

    private val _state: MutableStateFlow<StatsUiState> = MutableStateFlow(StatsUiState.Loading)
    val state: StateFlow<StatsUiState> get() = _state

    private var selectorType: StatChipType = StatChipType.ACTIVITY

    private lateinit var _allNotes: List<NoteModel>

    init {
        getNotes()
    }

    private fun getNotes() = viewModelScope.launch {
        try {
            delay(STATS_INIT_DELAY)
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
        val activitiesByMonth = notes
            .groupBy { note -> note.date.month }
            .toSortedMap()
            .mapKeys { keys -> keys.key.getDisplayName(TextStyle.SHORT, Locale.getDefault()) }
            .mapValues { value -> value.value.size }
        setStatContent(true, activitiesByMonth)
    }

    private fun setStatContent(
        isInitial: Boolean = false,
        activitiesByMonth: Map<String, Int> = mapOf(),
    ) = viewModelScope.launch {
        val statItems = getStats(selectorType)

        if (isInitial) {
            _state.value = StatsUiState.Content(
                statItems = statItems,
                averageRate = getAverageRate(),
                mostActiveMonth = getMostActiveMonth(),
                activitiesByMonth = activitiesByMonth,
                mostPopularActivity = getMostPopularActivity(),
                mostPopularLocation = getMostPopularLocation()
            )
        } else {
            _state.update {
                when (it) {
                    is StatsUiState.Content -> it.copy(statItems = statItems)
                    else -> it
                }
            }
        }
    }

    private fun getMostActiveMonth(): String? {
        val mostActiveMonth = _allNotes
            .groupBy { it.date.yearMonth }
            .mapValues { (_, notes) -> notes.size }
            .maxByOrNull { it.value }

        return mostActiveMonth?.key?.month?.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    private fun getAverageRate(): BigDecimal {
        val averageRate = _allNotes
            .map { it.experienceRate }
            .average()
            .toString()
            .toBigDecimal()
            .setScale(2, RoundingMode.HALF_UP)

        return averageRate
    }

    private fun getMostPopularActivity(): PracticeType? {
        val mostPopularActivity = _allNotes
            .map { it.types }
            .flatten()
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key

        return mostPopularActivity
    }

    private fun getMostPopularLocation(): PracticeLocation? {
        val mostPopularLocation = _allNotes
            .groupingBy { it.location }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key

        return mostPopularLocation
    }

    private suspend fun getStats(
        chipType: StatChipType,
    ) = withContext(Dispatchers.IO) {
        listOf<StatsItem>()
//        val allItemsAmount = _allNotes.size
//
//        val sortedNotes = _allNotes
//            .groupBy {
//                when (chipType) {
//                    StatChipType.ACTIVITY -> it.type
//                    StatChipType.PLACE -> it.location
//                }
//            }
//            .toList()
//            .sortedByDescending { (_, notes) -> notes.size }
//
//        if (sortedNotes.size == TOP_NOTES_AMOUNT + 1) {
//            return@withContext sortedNotes
//                .map { (type, notes) ->
//                    StatsItem(
//                        info = type,
//                        value = notes.size,
//                        percent = BigDecimal
//                            .valueOf(PERCENTAGE_100 * notes.size / allItemsAmount)
//                            .setScale(1, RoundingMode.HALF_UP)
//                    )
//                }
//                .sortedByDescending { it.percent }
//        }
//
//        val topNotes = sortedNotes.take(TOP_NOTES_AMOUNT)
//        val otherNotes = sortedNotes.drop(TOP_NOTES_AMOUNT)
//        val otherNotesSize = otherNotes.sumOf { (_, notes) -> notes.size }
//
//        val statItems = topNotes
//            .map { (type, notes) ->
//                StatsItem(
//                    info = type,
//                    value = notes.size,
//                    percent = BigDecimal
//                        .valueOf(PERCENTAGE_100 * notes.size / allItemsAmount)
//                        .setScale(1, RoundingMode.HALF_UP)
//                )
//            }
//
//        if (otherNotes.isNotEmpty()) {
//            statItems + StatsItem(
//                info = Other,
//                value = otherNotesSize,
//                percent = BigDecimal
//                    .valueOf(PERCENTAGE_100 * otherNotesSize / allItemsAmount)
//                    .setScale(1, RoundingMode.HALF_UP)
//            )
//        } else {
//            statItems
//        }
    }

    fun onIntentCreated(intent: StatsIntent) = viewModelScope.launch {
        when (intent) {
            is StatsIntent.ShowByType -> onTypeChanged(intent.type)
        }
    }

    private fun onTypeChanged(type: StatChipType) {
        selectorType = type
        setStatContent()
    }
}