package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.presentation.stats.model.TimeFrameItem
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Preview(showBackground = true)
@Composable
fun TimeframeView(
    modifier: Modifier = Modifier,
    onTimeframeChange: (TimeFrameItem) -> Unit = {}
) {

    val (selectedTimeFrame, setSelectedTimeFrame) = remember {
        mutableStateOf(TimeFrameItem.CURRENT_MONTH)
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Padding.medium, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(horizontal = Padding.small),
    ) {
        items(TimeFrameItem.entries) { item ->
            Text(
                modifier = Modifier
                    .padding( vertical = Padding.medium)
                    .fillMaxHeight()
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        setSelectedTimeFrame(item)
                        onTimeframeChange(item)
                    }
                ,
                text = stringResource(item.toStringId()),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = if (item == selectedTimeFrame) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                },
                fontWeight = if (item == selectedTimeFrame) {
                    FontWeight.SemiBold
                } else {
                    FontWeight.Normal
                },
            )
        }
    }
}