package com.antsfamily.naughtynotes.presentation.home.view

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.ui.theme.Padding


@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    value: String,
    @StringRes descriptionText: Int,
    containerColor: Color,
    contentColor: Color,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(Padding.medium),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Box(Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = value,
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(Padding.regular),
                style = MaterialTheme.typography.labelSmall,
                text = stringResource(descriptionText)
            )
        }
    }
}

@Preview
@Composable
private fun InfoCardPreview() {
    InfoCard(
        value = "Title",
        descriptionText = R.string.home_screen_title,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
}
