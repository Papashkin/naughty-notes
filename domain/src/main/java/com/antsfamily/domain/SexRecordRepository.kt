package com.antsfamily.domain

import com.antsfamily.domain.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface SexRecordRepository {
    val notes: Flow<List<NoteModel>>
    suspend fun getData(): List<NoteModel>
    suspend fun updateData()
    suspend fun saveData(note: NoteModel)
}