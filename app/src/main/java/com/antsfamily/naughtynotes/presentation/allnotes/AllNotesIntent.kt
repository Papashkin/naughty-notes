package com.antsfamily.naughtynotes.presentation.allnotes

import com.antsfamily.domain.model.NoteModel

sealed class AllNotesIntent {
    data object Retry : AllNotesIntent()
    data object AddNote : AllNotesIntent()
    data object BringDeletedNoteBack : AllNotesIntent()
    data object NoteSuccessfullyDeleted : AllNotesIntent()

    sealed class NoteCardIntent : AllNotesIntent() {
        data class EditNote(val note: NoteModel) : NoteCardIntent()
        data class DeleteNote(val note: NoteModel) : NoteCardIntent()
    }
}
