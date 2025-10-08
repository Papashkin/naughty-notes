package com.antsfamily.naughtynotes.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.home.view.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.pincode.PinCodeBottomSheetDialog
import com.antsfamily.naughtynotes.presentation.settings.view.SettingsContentScreen

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onCodeChangeClick: () -> Unit
) {
    val (isPinCodeDialogVisible, setIsPinCodeDialogVisible) = remember {
        mutableStateOf(false)
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.setPinCodeDialogVisibilityEvent.collect {
            setIsPinCodeDialogVisible(it)
        }
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        TopBar(
            title = stringResource(R.string.settings_screen_settings),
            onNavigationBack = { onNavigateBack() }
        )
        when (val uiState = state.value) {
            is SettingsUiState.Loading -> FullScreenLoading()
            is SettingsUiState.Content -> SettingsContentScreen(
                state = uiState,
                onPinClick = { viewModel.onPinClick(it) },
                onThemeChanged = { viewModel.onThemeChanged(it) },
                onCodeChangeClick = { onCodeChangeClick() }
            )
        }
    }

    if (isPinCodeDialogVisible) {
        PinCodeBottomSheetDialog(
            onSuccessfulPinSaved = { viewModel.onPinCodeSaved() },
            onDismiss = { setIsPinCodeDialogVisible(false) }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(onNavigateBack = {}, onCodeChangeClick = {})
}