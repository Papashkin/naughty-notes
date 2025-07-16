package com.antsfamily.data

import com.antsfamily.data.local.SexRecordDao
import com.antsfamily.data.model.toDTO
import com.antsfamily.domain.SexRecordRepository
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SexRecordRepositoryImpl @Inject constructor(
    private val dao: SexRecordDao
) : SexRecordRepository {
    override val notes: Flow<List<NoteModel>>
        get() = dao.allRecordsFlow().map { record ->
            record.map {
                NoteModel(
                    date = it.date,
                    type = SexType.valueOf(it.type),
                    isProtected = it.isProtected,
                    rate = it.pleasureRate,
                    painRate = it.painRate,
                    personalNote = it.note
                )
            }
        }

    override suspend fun getData(): List<NoteModel> {
        TODO("Not yet implemented")
    }

    override suspend fun updateData() {
        TODO("Not yet implemented")
    }

    override suspend fun saveData(note: NoteModel) {
        val record = note.toDTO()
        dao.addRecord(record)
    }
}