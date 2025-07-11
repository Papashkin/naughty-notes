package com.antsfamily.sexcalendar.presentation.createnote

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.sexcalendar.presentation.home.view.FullScreenLoading

@Composable
fun CreateNoteScreen(
    viewModel: CreateNoteViewModel = hiltViewModel(),
    onNavigateBack: ()-> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.navigateBackEvent.collect {
            onNavigateBack()
        }
    }

    val state = viewModel.state.collectAsState()

    when (val uiState = state.value) {
        is CreateNoteUiState.Content -> CreateNoteContent()
        is CreateNoteUiState.Loading -> FullScreenLoading()
    }
}

@Composable
fun CreateNoteContent() {
    //TODO create UI for note creation
}