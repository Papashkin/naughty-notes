package com.antsfamily.data

import com.antsfamily.data.local.NotesDao
import com.antsfamily.data.model.toDTO
import com.antsfamily.data.model.toModel
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NotesDao
) : NoteRepository {

    override val notes: Flow<List<NoteModel>>
        get() = dao.getNotesFlow()
            .flowOn(Dispatchers.IO)
            .map { record ->
                record.map { it.toModel() }
            }

    override suspend fun subscribeToNotesOnDate(date: LocalDate): Flow<List<NoteModel>> =
        dao.getNotesOnDateFlow(date)
            .flowOn(Dispatchers.IO)
            .map { record ->
                record.map {
                    it.toModel()
                }
            }

    override suspend fun getAllNotes(): List<NoteModel> = withContext(Dispatchers.IO) {
        val data = dao.getNotes()
        data.map { it.toModel() }
    }

    override suspend fun getNotesByMonthAndYear(month: Int, year: Int): List<NoteModel> {
        val data = dao.getNotes()
            .filter { it.date.year == year && it.date.monthValue == month }
        return data.map { it.toModel() }
    }

    override suspend fun getNotesByDate(date: LocalDate): List<NoteModel> {
        val data = dao.getNotes().filter { it.date == date }
        return data.map { it.toModel() }
    }

    override suspend fun getNoteById(id: Int): NoteModel? {
        return dao.getNoteById(id)?.toModel()
    }

    override suspend fun deleteNote(note: NoteModel) {
        val record = note.toDTO()
        dao.deleteNote(record)
    }

    override suspend fun addNote(note: NoteModel) {
        val record = note.toDTO()
        dao.addNote(record)
    }

    override suspend fun updateNote(note: NoteModel) {
        val record = note.toDTO()
        dao.updateNote(record)
    }
}