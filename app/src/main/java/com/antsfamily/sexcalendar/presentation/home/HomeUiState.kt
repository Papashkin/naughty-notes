package com.antsfamily.sexcalendar.presentation.home

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data class Content(val currentMonth: Int): HomeUiState()
}