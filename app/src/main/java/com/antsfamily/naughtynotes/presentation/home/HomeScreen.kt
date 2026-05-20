package com.antsfamily.naughtynotes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
    val view = LocalView.current

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is HomeNavigationEvent.NavigateToAllNotes -> navigateToAllNotes(event.date)
                is HomeNavigationEvent.NavigateToNoteForm -> navigateToNoteForm(event.date)
                HomeNavigationEvent.NavigateToSettings -> navigateToSettings()
            }

        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(horizontal = Padding.large)
    ) {
        HomeHeader(viewModel::handleIntent)
        when (val uiState = state.value) {
            is HomeUiState.Loading -> HomeContentLoadingView()
            is HomeUiState.Content -> HomeContentView(uiState, viewModel::handleIntent)
        }
    }
}

@Composable
fun HomeHeader(
    onIntentChanged: (HomeIntent) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.home_screen_title),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            IconButton(
                modifier = Modifier.align(Alignment.CenterEnd),
                onClick = { onIntentChanged(HomeIntent.Settings) }
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_settings),
                    contentDescription = stringResource(R.string.home_screen_banner_settings)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeHeader_Preview() {
    HomeHeader() {
        // no-op
    }
}