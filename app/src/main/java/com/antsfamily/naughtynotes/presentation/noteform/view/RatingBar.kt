package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    @StringRes scaleMinLabel: Int,
    @StringRes scaleMaxLabel: Int,
    selectedIcon: ImageVector,
    defaultIcon: ImageVector,
    onRatingChanged: (Int) -> Unit,
) {
    val starCount = 5
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Padding.tiny),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (i in 1..starCount) {
            Column(verticalArrangement = Arrangement.Center) {
                Icon(
                    imageVector = if (i <= rating) selectedIcon else defaultIcon,
                    contentDescription = "Star $i",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(Padding.small)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { onRatingChanged(i) }
                )
                if (i == 1) {
                    Text(
                        text = stringResource(scaleMinLabel),
                        modifier = Modifier.width(64.dp),
                        minLines = 2,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                if (i == starCount) {
                    Text(
                        text = stringResource(scaleMaxLabel),
                        modifier = Modifier.width(64.dp),
                        minLines = 2,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RatingBarPreview() {
    Column {

        RatingBar(
            rating = 4,
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_heart_filled),
            defaultIcon = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
            scaleMinLabel = R.string.note_form_screen_pain_rate_min_label,
            scaleMaxLabel = R.string.note_form_screen_pain_rate_max_label,
            onRatingChanged = {}
        )
        RatingBar(
            rating = 2,
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_broken_heart_filled),
            defaultIcon = ImageVector.vectorResource(R.drawable.ic_broken_heart_outlined),
            scaleMinLabel = R.string.note_form_screen_pleasure_rate_min_label,
            scaleMaxLabel = R.string.note_form_screen_pleasure_rate_max_label,
            onRatingChanged = {}
        )
    }
}