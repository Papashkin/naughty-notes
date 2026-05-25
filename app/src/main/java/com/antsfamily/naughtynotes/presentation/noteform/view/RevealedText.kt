package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun RevealedText(
    modifier: Modifier = Modifier,
    @StringRes textId: Int
) {
    var revealed by remember {
        mutableStateOf(false)
    }

    Text(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        revealed = true
                        tryAwaitRelease()
                        revealed = false
                    },
                )
            }
            .blur(
                radius = if (revealed) 0.dp else 12.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
        ,
        text = stringResource(textId),
        style = MaterialTheme.typography.bodySmall,
    )
}