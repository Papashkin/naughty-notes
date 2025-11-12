package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class GetNotesByDateUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(date: LocalDate): List<NoteModel> = withContext(Dispatchers.IO) {
        repository.getNotesByDate(date)
    }
}