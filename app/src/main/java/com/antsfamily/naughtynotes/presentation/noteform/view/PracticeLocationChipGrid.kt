package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.naughtynotes.presentation.common.ChipWithAnimation
import com.antsfamily.naughtynotes.presentation.noteform.model.PracticeLocationItem
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

private const val CHIP_GRID_COLLAPSED_ITEMS_MAX = 4
private const val CHIP_GRID_ROW_ITEMS_MAX = 3

@Composable
fun PracticeLocationChipGrid(
    items: List<PracticeLocationItem>,
    modifier: Modifier = Modifier,
    onChipClick: (PracticeLocation, Boolean) -> Unit
) {
    var isCollapsed by remember { mutableStateOf(true) }

    Column {
        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.medium),
            verticalArrangement = Arrangement.Center,
            itemVerticalAlignment = Alignment.CenterVertically,
            maxItemsInEachRow = CHIP_GRID_ROW_ITEMS_MAX,
            maxLines = if (isCollapsed) {
                1
            } else {
                Int.MAX_VALUE
            }
        ) {
            items.forEach { item ->
                ChipWithAnimation(
                    labelId = item.location.toStringId(),
                    isSelected = item.isSelected
                ) {
                    onChipClick(item.location, it)
                }
            }
        }
        if (items.size > CHIP_GRID_COLLAPSED_ITEMS_MAX) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(
                    onClick = {
                        isCollapsed = !isCollapsed
                    }
                ) {
                    Text(
                        modifier =
                            Modifier
                                .testTag("practice_location_grid_button_show_more"),
                        text = if (isCollapsed) {
                            "Show more"
                        } else {
                            "Show less"
                        }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview_PracticeLocationChipGrid() {
    Column {
        val items = PracticeLocation.entries
            .map { PracticeLocationItem(it, false) }

        PracticeLocationChipGrid(
            items = items,
        ) { _, _ ->
            // no-op
        }
    }
}