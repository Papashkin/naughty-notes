package com.antsfamily.naughtynotes.presentation.settings.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.settings.SettingsUiState
import com.antsfamily.naughtynotes.ui.theme.Padding


@Composable
fun SettingsContentScreen(
    state: SettingsUiState.Content,
    onPinClick: (Boolean) -> Unit,
    onThemeChanged: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Padding.medium)
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Text(
            stringResource(R.string.compose_settings_security),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = Padding.x_large)
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            headlineContent = {
                Text(
                    text = if (state.isAppProtected) {
                        stringResource(R.string.settings_screen_pin_on)
                    } else {
                        stringResource(R.string.settings_screen_pin_off)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = Padding.x_small)
                )
            },
            supportingContent = {
                Text(
                    text = stringResource(R.string.compose_settings_pin),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = Padding.tiny)
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
                    onCheckedChange = { onPinClick(it) },
                )
            }
        )

        if (state.isAppProtected) {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                headlineContent = {
                    Text(
                        text = stringResource(R.string.settings_pincode_title),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(top = Padding.x_small)
                    )
                },
                supportingContent = {
                    Text(
                        text = stringResource(R.string.settings_pincode_subtitle),
                        style = MaterialTheme.typography.labelMedium,
                    )
                },
                leadingContent = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_settings_security),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                    )
                },
                trailingContent = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                    )
                }
            )
        }

        Text(
            stringResource(R.string.compose_settings),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(top = Padding.xx_large)
        )

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            headlineContent = {
                Text(
                    text = if (state.isDarkMode) {
                        stringResource(R.string.settings_screen_theme_on)
                    } else {
                        stringResource(R.string.settings_screen_theme_off)
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = Padding.x_small)
                )
            },
            supportingContent = {
                Text(
                    text = stringResource(R.string.compose_settings_dark_mode),
                    style = MaterialTheme.typography.labelMedium,
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
                    checked = state.isDarkMode,
                    onCheckedChange = { onThemeChanged(it) },
                )
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SettingsContentScreenPreview() {
    SettingsContentScreen(
        state = SettingsUiState.Content(
            false,
            true
        ),
        {},
        {},
    )
}