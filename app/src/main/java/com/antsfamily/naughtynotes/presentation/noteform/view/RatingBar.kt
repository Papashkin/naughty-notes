package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.presentation.noteform.model.RatingType
import com.antsfamily.naughtynotes.presentation.noteform.model.toDefaultIcon
import com.antsfamily.naughtynotes.presentation.noteform.model.toSelectedIcon
import com.antsfamily.naughtynotes.ui.theme.Padding

private const val RATING_BAR_MAX_VALUE = 5

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    type: RatingType,
    rating: Int,
    onRatingChanged: (Int) -> Unit,
) {
    val selectedIcon = type.toSelectedIcon()
    val defaultIcon = type.toDefaultIcon()
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            Padding.large,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..RATING_BAR_MAX_VALUE) {
            val tag = ("rating_" + type.name).lowercase() + i
            Icon(
                imageVector =
                    if (i <= rating) {
                        ImageVector.vectorResource(selectedIcon)
                    } else {
                        ImageVector.vectorResource(defaultIcon)
                    },
                contentDescription = tag,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(52.dp)
                    .testTag(tag)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RatingBarPreview() {
    Column {

        RatingBar(
            rating = 4,
            type = RatingType.PLEASURE,
            onRatingChanged = {}
        )
        RatingBar(
            rating = 2,
            type = RatingType.PAIN,
            onRatingChanged = {}
        )
    }
}