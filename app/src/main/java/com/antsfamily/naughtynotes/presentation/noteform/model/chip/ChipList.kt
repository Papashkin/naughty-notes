package com.antsfamily.naughtynotes.presentation.noteform.model.chip

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun ChipList(
    modifier: Modifier = Modifier,
    chips: List<NoteChip>,
    onChipClick: (ChipType, Boolean) -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Padding.small),
        verticalArrangement = Arrangement.SpaceEvenly,
        itemVerticalAlignment = Alignment.CenterVertically,
        maxLines = 2
    ) {
        chips.forEach { chip ->

            ChipWithAnimation(
                labelId = chip.type.toStringId(),
                isSelected = chip.isSelected
            ) {
                onChipClick(chip.type, it)
            }
        }
    }
}

@Composable
fun ChipWithAnimation(
    @StringRes labelId: Int,
    isSelected: Boolean,
    onChipClick: (Boolean) -> Unit
) {
    val containerColor by animateColorAsState(
        targetValue =
            if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.surface
            },
        animationSpec = tween(250),
        label = "chip_container_animation"
    )

    val contentColor by animateColorAsState(
        targetValue =
            if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurface
            },
        animationSpec = tween(250),
        label = "chip_content_animation"
    )

    val scale by animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
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
        modifier = Modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
        },
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
    ) {
        Text(
            text = stringResource(labelId),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}