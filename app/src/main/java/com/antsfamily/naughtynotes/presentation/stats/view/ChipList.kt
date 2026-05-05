package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.naughtynotes.presentation.stats.model.CHIP_TYPE_DEFAULT
import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Preview(showBackground = true)
@Composable
fun StatsChipList(
    modifier: Modifier = Modifier,
    onChipClick: (StatChipType) -> Unit = {}
) {
    val chips = remember { StatChipType.entries }
    val (selectedTypeId, setSelectedTypeId) = remember { mutableStateOf(CHIP_TYPE_DEFAULT) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.extraLarge
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        chips.forEach { chip ->
            ChipItem(
                modifier = modifier.weight(1f),
                type = chip,
                isSelected = chip == selectedTypeId,
                onClick = {
                    setSelectedTypeId(chip)
                    onChipClick(chip)
                }
            )
        }
    }
}

@Composable
fun ChipItem(
    modifier: Modifier,
    type: StatChipType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = Padding.tiny, horizontal = Padding.x_small)
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                } else {
                    MaterialTheme.colorScheme.surfaceContainer
                },
                shape = MaterialTheme.shapes.large
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                if (!isSelected) onClick()
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.small)
            ,
            text = stringResource(type.toStringId()),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.outline
            },
        )
    }
}
