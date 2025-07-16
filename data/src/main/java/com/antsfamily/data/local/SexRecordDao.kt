package com.antsfamily.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.antsfamily.data.model.SexRecordDTO
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SexRecordDao {

    @Query("SELECT * from sexrecorddto")
    abstract suspend fun getAllRecords(): List<SexRecordDTO>

    @Query("SELECT * from sexrecorddto")
    abstract fun allRecordsFlow(): Flow<List<SexRecordDTO>>

    @Query("Select * from sexrecorddto where id = :id")
    abstract suspend fun getRecordById(id: Int): SexRecordDTO?

    @Insert
    abstract suspend fun addRecord(profile: SexRecordDTO)

    @Update
    abstract suspend fun updateRecord(profile: SexRecordDTO)
}