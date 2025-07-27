package com.antsfamily.sexcalendar.presentation.allnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antsfamily.domain.SexRecordRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Month

@HiltViewModel(assistedFactory = AllNotesViewModel.Factory::class)
class AllNotesViewModel @AssistedInject constructor(
    private val repository: SexRecordRepository,
    @Assisted("month") private val month: Month,
    @Assisted("year") private val year: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("month") month: Month,
            @Assisted("year") year: Int
        ): AllNotesViewModel
    }

    private val _navigationBackFlow = MutableSharedFlow<Unit>()
    val navigationBackFlow: SharedFlow<Unit> = _navigationBackFlow.asSharedFlow()

    private val _state = MutableStateFlow<AllNotesUiState>(AllNotesUiState.Loading)
    val state: StateFlow<AllNotesUiState> = _state.asStateFlow()

    init {
        getNotes()
    }

    private fun getNotes() = viewModelScope.launch {
        try {
            delay(200)
            val notes = repository.getNotesByMonthAndYear(month = month.value, year = year)
            _state.value = AllNotesUiState.Content(notes)
        } catch (e: Exception) {
            //TODO fix it later with error Type and it's handler
            _state.value = AllNotesUiState.Error(e.message.orEmpty())
        }

    }
}