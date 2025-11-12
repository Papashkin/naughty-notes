package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.antsfamily.naughtynotes.R
import com.lottiefiles.dotlottie.core.compose.runtime.DotLottieController
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieEventListener
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import kotlinx.coroutines.delay

private val SIZE_ANIMATION = 300.dp

@Preview(showBackground = true)
@Composable
fun SuccessDialog(
    onAnimationFinish: () -> Unit = {}
) {
    val dotLottieController = remember { DotLottieController() }

    LaunchedEffect(UInt) {
        delay(200)
        dotLottieController.play()
    }

    Dialog(
        onDismissRequest = {}
    ) {
        DotLottieAnimation(
            modifier = Modifier.size(SIZE_ANIMATION),
            autoplay = true,
            speed = 1.4f,
            source = DotLottieSource.Res(R.raw.anim_success),
            eventListeners = listOf(
                getCompleteLottieAnimationListener {
                    onAnimationFinish()
                }
            )
        )
    }
}

private fun getCompleteLottieAnimationListener(
    onFinish: () -> Unit
) = object : DotLottieEventListener {
    override fun onComplete() {
        onFinish()
    }
}