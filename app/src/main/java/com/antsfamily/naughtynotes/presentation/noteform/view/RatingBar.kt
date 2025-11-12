package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    selectedIcon: ImageVector,
    defaultIcon: ImageVector,
    onRatingChanged: (Int) -> Unit,
) {
    val starCount = 5
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            Padding.large,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..starCount) {
            Icon(
                imageVector = if (i <= rating) selectedIcon else defaultIcon,
                contentDescription = "Star $i",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(52.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { onRatingChanged(i) }
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
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_heart_filled),
            defaultIcon = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
            onRatingChanged = {}
        )
        RatingBar(
            rating = 2,
            selectedIcon = ImageVector.vectorResource(R.drawable.ic_broken_heart_filled),
            defaultIcon = ImageVector.vectorResource(R.drawable.ic_broken_heart_outlined),
            onRatingChanged = {}
        )
    }
}