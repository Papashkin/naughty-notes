package com.antsfamily.naughtynotes.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.home.view.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.settings.view.SettingsContentScreen

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .statusBarsPadding()
    ) {
        TopBar(
            title = stringResource(R.string.compose_settings_title),
            onNavigationBack = {
                onNavigateBack()
            }
        )
        when (val uiState = state.value) {
            is SettingsUiState.Loading -> FullScreenLoading()
            is SettingsUiState.Content -> SettingsContentScreen(
                state = uiState,
                onPinClick = { viewModel.onPinClick(it) },
                onThemeChanged = { viewModel.onThemeChanged(it) },
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(onNavigateBack = {})
}