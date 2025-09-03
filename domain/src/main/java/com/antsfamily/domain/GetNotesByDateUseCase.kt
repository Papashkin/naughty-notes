package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.NoteRepository
import java.time.LocalDate
import javax.inject.Inject

class GetNotesByDateUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(date: LocalDate): UseCaseResult<List<NoteModel>> = try {
        val notes = repository.getNotesByDate(date)
        UseCaseResult.Success(notes)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }
}