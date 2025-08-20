package com.antsfamily.naughtynotes.presentation.pincode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.view.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.noteform.model.LoadingButton
import com.antsfamily.naughtynotes.presentation.pincode.view.PinCodeKeyboard
import com.antsfamily.naughtynotes.presentation.pincode.view.PinCodeView
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun PinCodeScreen(
    viewModel: PinCodeViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val uiState = state.value) {
        is PinCodeUiState.Loading -> FullScreenLoading()
        is PinCodeUiState.Content -> ContentView(
            content = uiState,
            onKeyClick = { viewModel.onKeyClicked(it) },
            onShowCodeClick = { viewModel.onShowCodeClicked() },
            onDeleteClick = { viewModel.onDeleteClicked() }
        )

        is PinCodeUiState.Error -> TODO()
    }
}

@Composable
fun ContentView(
    content: PinCodeUiState.Content,
    onKeyClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onShowCodeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = Padding.large),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Icon(
                modifier = Modifier
                    .padding(top = Padding.medium)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .size(64.dp)
                    .padding(Padding.regular),
                imageVector = ImageVector.vectorResource(R.drawable.ic_lock),
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(top = Padding.large),
                text = stringResource(R.string.pin_code_screen_title),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                modifier = Modifier.padding(top = Padding.small),
                text = stringResource(R.string.pin_code_screen_subtitle),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            PinCodeView(
                modifier = Modifier.padding(Padding.large),
                isCodeVisible = content.isCodeVisible,
                code = content.code
            )

            PinCodeKeyboard(
                modifier = Modifier.padding(top = Padding.large),
                isCodeVisible = content.isCodeVisible,
                onKeyClick = { onKeyClick(it) },
                onDeleteClick = { onDeleteClick() },
                onShowCodeClick = { onShowCodeClick() }
            )
        }

        if (content.isSavePinButtonVisible) {
            LoadingButton(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                onClick = {
                },
            ) {
                Text(text = stringResource(R.string.pin_code_screen_button_save))
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ContentPreview() {
    ContentView(
        content = PinCodeUiState.Content(
            isCodeVisible = false,
            code = "23",
            isSavePinButtonVisible = false
        ),
        onKeyClick = {},
        onDeleteClick = {},
        onShowCodeClick = {}
    )
}