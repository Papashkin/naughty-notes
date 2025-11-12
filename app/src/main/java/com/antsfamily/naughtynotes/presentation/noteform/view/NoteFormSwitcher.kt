package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun NoteFormSwitcher(
    @StringRes titleId: Int,
    @DrawableRes iconId: Int,
    isChecked: Boolean,
    setNewValue: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(Padding.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            ImageVector.vectorResource(iconId),
            contentDescription = stringResource(titleId)
        )
        Text(
            text = stringResource(titleId),
            modifier = Modifier.padding(start = Padding.x_small),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.weight(1f))
        Switch(
            checked = isChecked,
            onCheckedChange = { setNewValue(it) }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteFormSwitcherPreview() {
    NoteFormSwitcher(
        titleId = R.string.note_form_screen_protection_label,
        iconId = R.drawable.ic_protection,
        isChecked = true) {

    }
}