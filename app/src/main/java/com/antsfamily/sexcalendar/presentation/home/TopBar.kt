package com.antsfamily.sexcalendar.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    isNavigationBackVisible: Boolean,
    isNavigationForwardVisible: Boolean,
    onNavigationBack: (() -> Unit)? = null,
    onNavigationForward: (() -> Unit)? = null
) {
    Row(
        modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier.padding(end = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = { if (isNavigationBackVisible) onNavigationBack?.invoke() }
            ) {
                if (isNavigationBackVisible) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            if (title != null) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        title,
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
        IconButton(
            onClick = { if (isNavigationForwardVisible) onNavigationForward?.invoke() }
        ) {
            if (isNavigationForwardVisible) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBarPreview1() {
    TopBar(
        title = "Some very long long title",
        isNavigationBackVisible = false,
        isNavigationForwardVisible = true,
        onNavigationBack = {},
        onNavigationForward = {},
    )
}


@Preview(showBackground = true)
@Composable
private fun TopBarPreview2() {
    TopBar(
        isNavigationBackVisible = true,
        isNavigationForwardVisible = false,
        onNavigationBack = {},
        onNavigationForward = null
    )
}