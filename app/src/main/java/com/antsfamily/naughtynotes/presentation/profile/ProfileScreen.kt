package com.antsfamily.naughtynotes.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.ui.theme.Padding

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreen(
    navigateBack: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onStatisticClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        TopBar(
            title = stringResource(R.string.profile_screen_title),
            onNavigationBack = { navigateBack() },
        )
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = Padding.medium)
        ) {
            ListItem(
                modifier = Modifier
                    .padding(vertical = Padding.small)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onStatisticClick()
                    }
                    .padding(top = Padding.medium),
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                headlineContent = {
                    Text(text = stringResource(R.string.statistic_screen_title))
                },
                leadingContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_settings_stats),
                        contentDescription = stringResource(R.string.settings_screen_title)
                    )
                },
                trailingContent = {
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null)
                }
            )
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            ListItem(
                modifier = Modifier
                    .padding(vertical = Padding.small)
                    .clickable {
                        onSettingsClick()
                    },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                headlineContent = {
                    Text(text = stringResource(R.string.settings_screen_title))

                },
                leadingContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_settings),
                        contentDescription = stringResource(R.string.settings_screen_title)
                    )
                },
                trailingContent = {
                    Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null)
                }
            )


        }
    }
}