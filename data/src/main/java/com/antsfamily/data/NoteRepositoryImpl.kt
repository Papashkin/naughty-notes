package com.antsfamily.data

import com.antsfamily.data.local.NotesDao
import com.antsfamily.data.model.toDTO
import com.antsfamily.domain.repository.NoteRepository
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NotesDao
) : NoteRepository {

    override val notes: Flow<List<NoteModel>>
        get() = dao.allRecordsFlow().map { record ->
            record.map {
                NoteModel(
                    id = it.id,
                    date = it.date,
                    type = SexType.valueOf(it.type),
                    isProtected = it.isProtected,
                    rate = it.pleasureRate,
                    painRate = it.painRate,
                    personalNote = it.note.orEmpty()
                )
            }
        }

    override suspend fun subscribeToNotesOnDate(date: LocalDate): Flow<List<NoteModel>> =
        dao.allRecordsOnDateFlow(date).map { record ->
            record.map {
                NoteModel(
                    id = it.id,
                    date = it.date,
                    type = SexType.valueOf(it.type),
                    isProtected = it.isProtected,
                    rate = it.pleasureRate,
                    painRate = it.painRate,
                    personalNote = it.note.orEmpty()
                )
            }
        }

    override suspend fun getAllNotes(): List<NoteModel> {
        val data = dao.getAllRecords()
        return data.map {
            NoteModel(
                id = it.id,
                date = it.date,
                type = SexType.valueOf(it.type),
                isProtected = it.isProtected,
                rate = it.pleasureRate,
                painRate = it.painRate,
                personalNote = it.note.orEmpty()
            )
        }
    }

    override suspend fun getNotesByMonthAndYear(month: Int, year: Int): List<NoteModel> {
        val data = dao.getAllRecords()
            .filter { it.date.year == year && it.date.monthValue == month }
        return data.map {
            NoteModel(
                id = it.id,
                date = it.date,
                type = SexType.valueOf(it.type),
                isProtected = it.isProtected,
                rate = it.pleasureRate,
                painRate = it.painRate,
                personalNote = it.note.orEmpty()
            )
        }
    }

    override suspend fun getNotesByDate(date: LocalDate): List<NoteModel> {
        val data = dao.getAllRecords().filter { it.date == date }
        return data.map {
            NoteModel(
                id = it.id,
                date = it.date,
                type = SexType.valueOf(it.type),
                isProtected = it.isProtected,
                rate = it.pleasureRate,
                painRate = it.painRate,
                personalNote = it.note.orEmpty()
            )
        }
    }

    override suspend fun getNoteById(id: Int): NoteModel? {
        return dao.getRecordById(id)?.let {
            NoteModel(
                id = it.id,
                date = it.date,
                type = SexType.valueOf(it.type),
                isProtected = it.isProtected,
                rate = it.pleasureRate,
                painRate = it.painRate,
                personalNote = it.note.orEmpty()
            )
        }
    }

    override suspend fun deleteNote(note: NoteModel) {
        val record = note.toDTO()
        dao.deleteRecord(record)
    }

    override suspend fun addNote(note: NoteModel) {
        val record = note.toDTO()
        dao.addRecord(record)
    }

    override suspend fun updateNote(note: NoteModel) {
        val record = note.toDTO()
        dao.updateRecord(record)
    }
}