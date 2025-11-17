package com.antsfamily.naughtynotes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.view.HomeContentLoadingView
import com.antsfamily.naughtynotes.presentation.home.view.HomeContentView
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNoteForm: (Long) -> Unit,
    navigateToAllNotes: (Long) -> Unit,
    navigateToSettings: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.navigateToNoteFormEvent.collect {
            navigateToNoteForm(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.navigateToAllNotesEvent.collect {
            navigateToAllNotes(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.navigateToSettingsEvent.collect {
            navigateToSettings()
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(horizontal = Padding.large)
    ) {
        HomeHeader()
        when (val uiState = state.value) {
            is HomeUiState.Loading -> HomeContentLoadingView()
            is HomeUiState.Content -> HomeContentView(uiState, viewModel::handleIntent)
        }
    }
}

@Composable
fun HomeHeader() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.home_screen_title),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.height(Padding.medium))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.home_screen_subtitle),
        style = MaterialTheme.typography.bodyMedium
    )
}