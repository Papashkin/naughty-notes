package com.antsfamily.sexcalendar.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.sexcalendar.R

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.navigationToHomeFlow.collect {
            navigateToHome()
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_app),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashViewWithIconPreview() {
    SplashViewWithIcon()
}
