package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceRate
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceType
import com.antsfamily.naughtynotes.presentation.util.toAlphaValue
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

private const val NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS = 300

@Composable
fun NoteFormSlider(
    rate: ExperienceRate,
    modifier: Modifier = Modifier,
    onValueChanged: (Float) -> Unit,
) {
    val valueRange = ExperienceType.toClosedRange()

    val rateAlpha = remember(rate) {
        rate.type.toAlphaValue()
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.large)
    ) {
        AnimatedContent(
            targetState = rate.type,
            transitionSpec = {
                if (targetState > initialState) {
                    slideInVertically(
                        initialOffsetY = { -it / 2 },
                        animationSpec = tween(NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS)
                    ) + fadeIn(
                        animationSpec = tween(NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS)
                    ) togetherWith
                            slideOutVertically(
                                targetOffsetY = { it / 2 },
                                animationSpec = tween(NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS)
                            ) + fadeOut(
                        animationSpec = tween(NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS)
                    )
                } else {
                    slideInVertically(
                        initialOffsetY = { it / 2 },
                        animationSpec = tween(NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS)
                    ) + fadeIn(
                        animationSpec = tween(NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS)
                    ) togetherWith
                            slideOutVertically(
                                targetOffsetY = { -it / 2 },
                                animationSpec = tween(NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS)
                            ) + fadeOut(
                        animationSpec = tween(NOTE_FORM_SLIDER_ANIMATION_DURATION_MILLIS)
                    )
                }
            },
            label = "rate_text_animation"
        ) { targetType ->
            Text(
                text = stringResource(targetType.toStringId()),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Slider(
            value = rate.value,
            onValueChange = {
                onValueChanged(it)
            },
            valueRange = valueRange,
            steps = 4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Padding.large),
            colors = SliderDefaults.colors(
                activeTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = rateAlpha),
                inactiveTrackColor = MaterialTheme.colorScheme.onPrimary,
            )
        )
    }
}