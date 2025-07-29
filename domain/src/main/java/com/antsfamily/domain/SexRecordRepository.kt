package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface SexRecordRepository {
    val notes: Flow<List<NoteModel>>
    suspend fun getAllNotes(): List<NoteModel>
    suspend fun getNotesByMonthAndYear(month: Int, year: Int): List<NoteModel>
    suspend fun getNotesByDate(date: LocalDate): List<NoteModel>
    suspend fun updateData()
    suspend fun saveData(note: NoteModel)
}