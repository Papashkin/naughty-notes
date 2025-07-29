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
import java.time.LocalDate

@HiltViewModel(assistedFactory = AllNotesViewModel.Factory::class)
class AllNotesViewModel @AssistedInject constructor(
    private val repository: SexRecordRepository,
    @Assisted("epoch") private val epoch: Long,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("epoch") epoch: Long,
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
            val date = LocalDate.ofEpochDay(epoch)
            val notes = repository.getNotesByDate(date)
            delay(200)
            _state.value = AllNotesUiState.Content(date, notes)
        } catch (e: Exception) {
            //TODO fix it later with error Type and it's handler
            _state.value = AllNotesUiState.Error(e.message.orEmpty())
        }

    }
}