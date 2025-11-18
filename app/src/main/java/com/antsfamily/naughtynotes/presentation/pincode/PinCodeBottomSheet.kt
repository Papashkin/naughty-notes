package com.antsfamily.naughtynotes.presentation.pincode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.noteform.model.LoadingButton
import com.antsfamily.naughtynotes.presentation.pincode.view.PinCodeKeyboard
import com.antsfamily.naughtynotes.presentation.pincode.view.PinCodeView
import com.antsfamily.naughtynotes.ui.theme.Padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinCodeBottomSheetDialog(
    viewModel: PinCodeViewModel = hiltViewModel(),
    onSuccessfulPinSaved: () -> Unit,
    onDismiss: () -> Unit,
) {
    val context = LocalContext.current

    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.showSuccessfulPinSaveFlow.collect {
            onSuccessfulPinSaved()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.dismissDialogFlow.collect {
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = { viewModel.onDialogDismissed() },
        sheetState = SheetState(
            density = Density(context),
            skipPartiallyExpanded = true
        ),
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Padding.large),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.medium
                    )
                    .size(64.dp)
                    .padding(Padding.regular),
                imageVector = ImageVector.vectorResource(R.drawable.ic_lock),
                contentDescription = null
            )

            Text(
                modifier = Modifier.padding(top = Padding.medium),
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
                modifier = Modifier.padding(Padding.small),
                isCodeVisible = state.value.isCodeVisible,
                code = state.value.code
            )

            PinCodeKeyboard(
                isCodeVisible = state.value.isCodeVisible,
                onKeyClick = { viewModel.onKeyClicked(it) },
                onDeleteClick = { viewModel.onDeleteClicked() },
                onShowCodeClick = { viewModel.onShowCodeClicked() }
            )

            LoadingButton(
                modifier = Modifier
                    .padding(Padding.large)
                    .fillMaxWidth(),
                enabled = state.value.isSaveButtonEnabled,
                onClick = { viewModel.onSaveButtonClicked() },
            ) {
                Text(text = stringResource(R.string.pin_code_screen_button_save))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PinCodeBottomSheetDialogPreview() {
    PinCodeBottomSheetDialog(onSuccessfulPinSaved = {}, onDismiss = {})
}
