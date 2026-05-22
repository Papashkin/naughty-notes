package com.antsfamily.naughtynotes.presentation.noteform.view

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
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun BubbleSlider(
    rate: ExperienceRate,
    modifier: Modifier = Modifier,
    onValueChanged: (Float) -> Unit,
) {
    val valueRange = ExperienceType.BAD.minValue..ExperienceType.AMAZING.maxValue

    val rateAlpha = remember(rate) {
        when (rate.type) {
            ExperienceType.BAD -> 0.2f
            ExperienceType.BELOW_AVERAGE -> 0.4f
            ExperienceType.OKAY -> 0.6f
            ExperienceType.GOOD -> 0.8f
            ExperienceType.AMAZING -> 1.0f
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Padding.large)
    ) {
        Text(
            text = stringResource(rate.type.toStringId()),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Slider(
            value = rate.value,
            onValueChange = {
                onValueChanged(it)
            },
            valueRange = valueRange,
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