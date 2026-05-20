package com.antsfamily.naughtynotes.presentation.home

sealed class HomeNavigationEvent {
    data class NavigateToNoteForm(val date: Long) : HomeNavigationEvent()
    data class NavigateToAllNotes(val date: Long) : HomeNavigationEvent()
    object NavigateToSettings : HomeNavigationEvent()
}