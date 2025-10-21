package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun ChipList(
    chips: List<StatChipType>,
    modifier: Modifier = Modifier,
    onChipClick: (StatChipType) -> Unit = {}
) {
    val (selectedTypeId, setSelectedTypeId) = remember { mutableStateOf(chips.first()) }

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Padding.regular),
        contentPadding = PaddingValues(horizontal = Padding.medium)
    ) {
        items(chips) { chip ->
            ChipItem(
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
    type: StatChipType,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = { if (!isSelected) onClick() },
        shape = RoundedCornerShape(Padding.regular),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.secondary
            },
            contentColor = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSecondary
            },
        ),
    ) {
        Text(
            text = stringResource(type.toStringId()),
            textAlign = TextAlign.Center
        )
    }
}


@Preview
@Composable
private fun ChipListPreview() {
    ChipList(
        chips = StatChipType.entries
    )
}