package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.domain.model.UseCaseResult
import com.antsfamily.domain.repository.NoteRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

class SaveOrUpdateNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {

    suspend operator fun invoke(
        id: Int?,
        date: LocalDate,
        types: List<PracticeType>,
        location: PracticeLocation,
        isProtected: Boolean,
        hasOrgasm: Boolean,
        hasPartnerOrgasm: Boolean,
        experienceRate: Float,
        personalNote: String,
    ): UseCaseResult<Unit> = try {
        val note = NoteModel(
            id = id ?: Random.nextInt(),
            date = date,
            types = types,
            location = location,
            isProtected = isProtected,
            hasOrgasm = hasOrgasm,
            hasPartnerOrgasm = hasPartnerOrgasm,
            experienceRate = experienceRate,
            personalNote = personalNote
        )

        id?.let {
            updateNote(note)
        } ?: run {
            addNote(note)
        }
        UseCaseResult.Success(Unit)
    } catch (e: Exception) {
        UseCaseResult.Error(e)
    }

    private suspend fun addNote(note: NoteModel) {
        repository.addNote(note)
    }

    private suspend fun updateNote(note: NoteModel) {
        repository.updateNote(note)
    }
}