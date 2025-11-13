package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.ui.theme.Padding


@Composable
fun HorizontalDividerWithText(
    modifier: Modifier = Modifier,
    text: String
) {
    Column(modifier = modifier) {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)
        )
        Text(
            modifier = Modifier.padding(
                start = Padding.large,
                top = Padding.small,
            ),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = text
        )
    }
}