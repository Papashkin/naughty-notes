package com.antsfamily.naughtynotes.presentation.settings.view

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.antsfamily.naughtynotes.presentation.settings.SettingsUiState
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun SettingsContentScreen(
    state: SettingsUiState.Content,
    onPinClick: (Boolean) -> Unit,
    onThemeChanged: (Boolean) -> Unit,
    onCodeChangeClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(start = Padding.medium, end = Padding.medium, top = Padding.huge)
    ) {
        Column {
            Text(
                stringResource(R.string.settings_screen_settings_security),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = Padding.x_large)
            )

            ListItem(
                modifier = Modifier.padding(top = Padding.medium),
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface
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
                        onCheckedChange = { onPinClick(it) },
                    )
                }
            )

            if (state.isAppProtected) {
                Button(
                    modifier = Modifier.padding(
                        start = Padding.medium,
                        end = Padding.medium,
                        bottom = Padding.medium
                    ),
                    onClick = { onCodeChangeClick() }
                ) {
                    Text(
                        text = stringResource(R.string.settings_screen_pincode_change_PIN),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            } else {
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
            }

            Text(
                stringResource(R.string.settings_screen_appearance_title),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = Padding.xx_large)
            )

            ListItem(
                modifier = Modifier.padding(top = Padding.medium),
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surface
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
                        onCheckedChange = { onThemeChanged(it) },
                    )
                }
            )
            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        ) {
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = state.appVersion.orEmpty(),
                style = MaterialTheme.typography.labelSmall,
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
            "1.0"
        ),
        {},
        {},
        {},
    )
}