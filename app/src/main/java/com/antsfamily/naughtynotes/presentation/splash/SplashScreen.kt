package com.antsfamily.naughtynotes.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.domain.model.ErrorType
import com.antsfamily.naughtynotes.R

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToCheckPin: () -> Unit,
    showLockSnackbar: (String) -> Unit,
    showErrorSnackbar: (ErrorType) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.navigationToHomeFlow.collect {
            navigateToHome()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.navigationToPinVerificationFlow.collect {
            navigateToCheckPin()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.showAppLockSnackbarFlow.collect {
            showLockSnackbar(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.showErrorSnackbarFlow.collect {
            showErrorSnackbar(it)
        }
    }

    SplashViewWithIcon()
}

@Composable
fun SplashViewWithIcon() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_app),
            contentDescription = "application icon"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashViewWithIconPreview() {
    SplashViewWithIcon()
}
