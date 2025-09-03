package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(note: NoteModel) = try {
        repository.deleteNote(note)
        UseCaseResult.Success(Unit)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}