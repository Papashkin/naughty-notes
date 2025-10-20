package com.antsfamily.naughtynotes.presentation.stats

import androidx.lifecycle.ViewModel
import com.antsfamily.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: NoteRepository,
) : ViewModel() {

    private val _state: StateFlow<StatsUiState> = MutableStateFlow(StatsUiState.Loading)
    val state: StateFlow<StatsUiState> get() = _state


}