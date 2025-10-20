package com.antsfamily.naughtynotes.presentation.changepincode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.antsfamily.naughtynotes.presentation.noteform.model.LoadingButton
import com.antsfamily.naughtynotes.presentation.pincode.view.PinCodeKeyboard
import com.antsfamily.naughtynotes.presentation.pincode.view.PinCodeView
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun ChangeExistingPinCodeScreen(
    viewModel: ChangeExistingPinCodeViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    onSuccessfulPinChanged: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateBackFlow.collect {
            navigateBack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.showSuccessfulPinSaveFlow.collect {
            onSuccessfulPinChanged()
        }
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(Padding.large),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
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
            modifier = Modifier.padding(top = Padding.medium),
            text = stringResource(R.string.change_existing_pin_code_screen_title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )

        Text(
            modifier = Modifier.padding(top = Padding.small),
            text = stringResource(state.value.step.toStringId()),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )

        PinCodeView(
            modifier = Modifier.padding(Padding.small),
            isCodeVisible = state.value.isCodeVisible,
            code = state.value.code,
            isError = state.value.isErrorVisible
        )

        if (state.value.errorType != null) {
            Text(
                modifier = Modifier.height(40.dp),
                text = stringResource(state.value.errorType!!.toStringId()),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        } else {
            Spacer(modifier = Modifier.height(40.dp))
        }

        PinCodeKeyboard(
            modifier = Modifier.padding(top = Padding.large),
            isCodeVisible = state.value.isCodeVisible,
            onKeyClick = { viewModel.onKeyClicked(it) },
            onDeleteClick = { viewModel.onDeleteClicked() },
            onShowCodeClick = { viewModel.onShowCodeClicked() }
        )

        Spacer(Modifier.weight(1f))

        LoadingButton(
            onClick = { viewModel.onProceedClicked() },
            enabled = state.value.isProceedButtonEnabled,
            loading = state.value.isProceedButtonLoadingVisible
        ) {
            Text(
                text = stringResource(state.value.proceedButtonState.toStringId())
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PinCodeVerificationScreenPreview() {
    ChangeExistingPinCodeScreen(
        onSuccessfulPinChanged = {},
        navigateBack = {}
    )
}
