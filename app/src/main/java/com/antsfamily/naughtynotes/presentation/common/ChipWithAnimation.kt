package com.antsfamily.naughtynotes.presentation.common

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

private const val CHIP_ANIMATION_DURATION = 250

@Composable
fun ChipWithAnimation(
    @StringRes labelId: Int,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onChipClick: (Boolean) -> Unit
) {
    val containerColor by animateColorAsState(
        targetValue =
            if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onPrimary
            },
        animationSpec = tween(CHIP_ANIMATION_DURATION),
        label = "chip_container_animation"
    )

    val contentColor by animateColorAsState(
        targetValue =
            if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
        animationSpec = tween(CHIP_ANIMATION_DURATION),
        label = "chip_content_animation"
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.02f else 0.97f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "chip_scale_animation"
    )

    Button(
        onClick = {
            onChipClick(!isSelected)
        },
        modifier =
            modifier
                .height(42.dp)
                .padding(Padding.x_small)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ) {
        Text(
            text = stringResource(labelId),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun Preview_ChipWithAnimation() {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Padding.x_small),
        verticalArrangement = Arrangement.SpaceEvenly,
        itemVerticalAlignment = Alignment.CenterVertically,
    ) {
        PracticeLocation.entries.forEachIndexed { index, location ->
            val isSelected = index % 2 == 0
            ChipWithAnimation(
                labelId = location.toStringId(),
                isSelected = isSelected
            ) {
                // no-op
            }

        }
    }

}
