package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(id: Int): NoteModel? {
        return repository.getNoteById(id)
    }
}