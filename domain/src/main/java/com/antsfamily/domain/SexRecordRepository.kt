package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface SexRecordRepository {
    val notes: Flow<List<NoteModel>>
    suspend fun getAllNotes(): List<NoteModel>
    suspend fun getNotesByMonthAndYear(month: Int, year: Int): List<NoteModel>
    suspend fun updateData()
    suspend fun saveData(note: NoteModel)
}