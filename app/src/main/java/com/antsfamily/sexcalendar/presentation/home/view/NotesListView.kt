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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NotesListView(
    modifier: Modifier = Modifier,
    notes: List<String>
) {
    Column(modifier = modifier) {
        Text("Notes", style = MaterialTheme.typography.titleMedium)
        if (notes.isNotEmpty()) {
            NotesListWithContent(notes)
        } else {
            NotesListEmpty()
        }
    }
}

@Composable
fun NotesListWithContent(notes: List<String>) {
    LazyColumn {
        items(notes) {
            NoteCard(it)
        }
    }
}

@Composable
fun NotesListEmpty() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "Don't have a note recorded?",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier.clickable {
                    //TODO add action
                },
                text = AnnotatedString(
                    text = "Create a note",
                    spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
                ),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun NoteCard(note: String) {
    ListItem(
        headlineContent = {
            Text(note)
        },
        leadingContent = {
            Icon(Icons.Outlined.Favorite, null)
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotesListViewPreview1() {
    NotesListView(notes = listOf("mock 1", "mock 2", "mock 3"))
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotesListViewPreview2() {
    NotesListView(notes = listOf())
}