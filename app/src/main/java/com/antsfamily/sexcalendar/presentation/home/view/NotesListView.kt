package com.antsfamily.sexcalendar.presentation.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import com.antsfamily.sexcalendar.R
import com.antsfamily.sexcalendar.presentation.createnote.model.toStringId
import com.antsfamily.sexcalendar.ui.theme.Padding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun NotesListView(
    modifier: Modifier = Modifier,
    notes: List<NoteModel>,
    onCreateNoteClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Notes",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = Padding.small)
        )
        if (notes.isNotEmpty()) {
            NotesListWithContent(notes)
        } else {
            NotesListEmpty(onCreateNoteClick)
        }
    }
}

@Composable
fun NotesListWithContent(notes: List<NoteModel>) {
    LazyColumn {
        items(notes) {
            NoteCard(it)
        }
    }
}

@Composable
fun NotesListEmpty(onCreateNoteClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Padding.regular)
        ) {
            Text(
                text = stringResource(R.string.home_screen_empty_content_label),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier.clickable { onCreateNoteClick() },
                text = AnnotatedString(
                    text = stringResource(R.string.home_screen_empty_content_button_create_note),
                    spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
                ),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun NoteCard(note: NoteModel) {
    ListItem(
        overlineContent = {
            Text(note.date.format(DateTimeFormatter.ISO_DATE))
        },
        headlineContent = {
            Text(stringResource(note.type.toStringId()))
        },
        leadingContent = {
            Icon(Icons.Outlined.Favorite, null)
        },
        supportingContent = {
            Text(note.personalNote)
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotesListViewPreview1() {
    NotesListView(
        notes = listOf(
            NoteModel(
                LocalDate.now(),
                SexType.ANAL,
                isProtected = true,
                painRate = 2,
                rate = 4,
                personalNote = ""
            ),
            NoteModel(
                LocalDate.now(),
                SexType.VAGINAL,
                isProtected = true,
                painRate = 2,
                rate = 4,
                personalNote = "That was something crazy"
            )
        )
    ) {}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotesListViewPreview2() {
    NotesListView(notes = listOf()) {}
}