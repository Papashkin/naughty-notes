package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.common.ChipWithAnimation
import com.antsfamily.naughtynotes.presentation.noteform.model.PracticeTypeItem
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun PracticeTypeChipGrid(
    chipList: List<PracticeTypeItem>,
    modifier: Modifier = Modifier,
    onChipClick: (PracticeType, Boolean) -> Unit
) {
    Column {
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(Padding.small),
            verticalArrangement = Arrangement.Center,
            itemVerticalAlignment = Alignment.CenterVertically,
        ) {
            chipList.forEach { chip ->
                ChipWithAnimation(
                    labelId = chip.type.toStringId(),
                    isSelected = chip.isSelected
                ) {
                    onChipClick(chip.type, it)
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview_PracticeTypeChipGrid() {
    Column {
        val chipItems = PracticeType.entries
            .map { PracticeTypeItem(it, false) }

        PracticeTypeChipGrid(
            chipList = chipItems,
        ) { _, _ ->
            // no-op
        }
    }
}
