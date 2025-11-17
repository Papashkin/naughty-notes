package com.antsfamily.naughtynotes.presentation.settings.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.settings.SettingsIntent
import com.antsfamily.naughtynotes.presentation.settings.SettingsUiState
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun SettingsContentScreen(
    state: SettingsUiState.Content,
    onActionClick: (SettingsIntent) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxHeight()
            .padding(start = Padding.medium, end = Padding.medium, top = Padding.gigantic)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(12.dp)
                    ),
            ) {
                Text(
                    modifier = Modifier.padding(Padding.medium),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = "Information"
                )

                ListItem(
                    modifier = Modifier
                        .padding(vertical = Padding.x_small)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            onActionClick(SettingsIntent.OpenStatistics)
                        },
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.statistic_screen_title),
                            style = MaterialTheme.typography.bodyMedium,
                        )
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
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Padding.large)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Text(
                    modifier = Modifier.padding(Padding.medium),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = stringResource(R.string.settings_screen_settings_security)
                )

                ListItem(
                    modifier = Modifier
                        .padding(vertical = Padding.x_small)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.settings_screen_settings_pin),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_settings_keypad),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null,
                        )
                    },
                    trailingContent = {
                        Switch(
                            checked = state.isAppProtected,
                            onCheckedChange = { onActionClick(SettingsIntent.SetPin(it)) },
                        )
                    }
                )

                if (state.isAppProtected) {
                    ListItem(
                        modifier = Modifier
                            .padding(bottom = Padding.x_small)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onActionClick(SettingsIntent.ChangePin) },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer
                        ),
                        headlineContent = {
                            Text(
                                text = stringResource(R.string.settings_screen_pincode_change_PIN),
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        },
                        trailingContent = {
                            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null)
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Padding.large)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {

                Text(
                    modifier = Modifier.padding(Padding.medium),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = stringResource(R.string.settings_screen_appearance_title)
                )

                ListItem(
                    modifier = Modifier
                        .padding(vertical = Padding.x_small)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    ),
                    headlineContent = {
                        Text(
                            text = stringResource(R.string.settings_screen_settings_dark_mode),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    leadingContent = {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_settings_palette),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null,
                        )
                    },
                    trailingContent = {
                        Switch(
                            modifier = Modifier
                                .semantics { testTagsAsResourceId = true }
                                .testTag("dark_mode_switch"),
                            checked = state.isDarkMode,
                            onCheckedChange = { onActionClick(SettingsIntent.SwitchTheme(it)) },
                        )
                    }
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        ) {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = state.appVersion.orEmpty(),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Padding.x_small)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsContentScreenPreview() {
    SettingsContentScreen(
        state = SettingsUiState.Content(
            isAppProtected = true,
            isDarkMode = false,
            "1.0 (122)"
        ),
        {},
    )
}