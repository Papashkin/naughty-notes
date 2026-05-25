package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.animateBounds
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.naughtynotes.presentation.common.ChipWithAnimation
import com.antsfamily.naughtynotes.presentation.noteform.model.LocationChipGridState
import com.antsfamily.naughtynotes.presentation.noteform.model.LocationChipGridState.COLLAPSED
import com.antsfamily.naughtynotes.presentation.noteform.model.LocationChipGridState.EXPANDED
import com.antsfamily.naughtynotes.presentation.noteform.model.PracticeLocationItem
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

const val CHIP_GRID_COLLAPSED_ITEMS_MAX = 3

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PracticeLocationChipGrid(
    items: List<PracticeLocationItem>,
    state: LocationChipGridState,
    modifier: Modifier = Modifier,
    onChipClick: (PracticeLocation, Boolean) -> Unit,
    onStateChanged: (LocationChipGridState) -> Unit,
) {
    val visibleItems = remember(state, items) {
        when (state) {
            COLLAPSED -> items.take(CHIP_GRID_COLLAPSED_ITEMS_MAX)
            EXPANDED -> items
        }
    }

    Column {
        LookaheadScope {
            FlowRow(
                modifier = modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .padding(horizontal = Padding.small),
                verticalArrangement = Arrangement.Center,
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                visibleItems.forEach { item ->
                    key(item.location) {
                        Box(
                            modifier = Modifier.animateBounds(this@LookaheadScope)
                        ) {
                            ChipWithAnimation(
                                labelId = item.location.toStringId(),
                                isSelected = item.isSelected
                            ) {
                                onChipClick(item.location, it)
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        when (state) {
                            COLLAPSED -> onStateChanged(EXPANDED)
                            EXPANDED -> onStateChanged(COLLAPSED)
                        }
                    },
                    modifier =
                        modifier
                            .height(42.dp)
                            .padding(Padding.x_small)
                            .animateBounds(this@LookaheadScope),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Text(
                        modifier = Modifier.testTag("practice_location_grid_button_show_more"),
                        style = MaterialTheme.typography.bodySmall,
                        text = stringResource(state.toStringId())
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview_PracticeLocationChipGrid() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Padding.medium),
    ) {
        val items = PracticeLocation.entries
            .map { PracticeLocationItem(it, false) }

        Text("Collapsed grid")
        PracticeLocationChipGrid(
            state = COLLAPSED,
            items = items,
            onStateChanged = {},
            onChipClick = { _, _ -> }
        )

        Text("Expanded grid")
        PracticeLocationChipGrid(
            state = EXPANDED,
            items = items,
            onStateChanged = {},
            onChipClick = { _, _ -> }
        )
    }
}