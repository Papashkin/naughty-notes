package com.antsfamily.naughtynotes.presentation.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.antsfamily.domain.model.ErrorType
import com.antsfamily.naughtynotes.R

const val SPLASH_SCREEN_ANIMATION_DURATION = 1000

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

    val infiniteTransition = rememberInfiniteTransition(
        label = "pulse_transition"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = SPLASH_SCREEN_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_app),
            contentDescription = "application icon",
            modifier = Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashViewWithIconPreview() {
    SplashViewWithIcon()
}
