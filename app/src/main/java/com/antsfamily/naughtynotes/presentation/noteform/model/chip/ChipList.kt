package com.antsfamily.naughtynotes.presentation.noteform.model.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.antsfamily.naughtynotes.presentation.common.ChipWithAnimation
import com.antsfamily.naughtynotes.presentation.util.toStringId
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