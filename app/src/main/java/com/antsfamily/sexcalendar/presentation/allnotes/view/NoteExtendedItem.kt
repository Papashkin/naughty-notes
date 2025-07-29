package com.antsfamily.sexcalendar.presentation.allnotes.view

import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import java.time.LocalDate

@Composable
fun NoteExtendedItem(
    index: Int,
    note: NoteModel,
    onDelete: (NoteModel) -> Unit,
    onEdit: (NoteModel) -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> onDelete(note)
                SwipeToDismissBoxValue.EndToStart -> onEdit(note)
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { DismissBackground(dismissState) },
        content = {
            NoteCardExtended(note, index)
        }
    )
}

@Preview
@Composable
private fun NoteExtendedItemPreview() {
    NoteExtendedItem(
        index = 2,
        note = NoteModel(
            LocalDate.now(),
            SexType.TRIBADISM,
            true,
            1,
            "something really-really long has written here just to see how it looks",
            5
        ),
        onDelete = {},
        onEdit = {}
    )
}