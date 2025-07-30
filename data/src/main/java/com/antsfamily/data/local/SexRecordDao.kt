package com.antsfamily.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.antsfamily.data.model.SexRecordDTO
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
abstract class SexRecordDao {

    @Query("SELECT * from sexrecorddto")
    abstract suspend fun getAllRecords(): List<SexRecordDTO>

    @Query("SELECT * from sexrecorddto")
    abstract fun allRecordsFlow(): Flow<List<SexRecordDTO>>

    @Query("Select * from sexrecorddto where id = :id")
    abstract suspend fun getRecordById(id: Int): SexRecordDTO?

    @Query("SELECT * from sexrecorddto where date = :date")
    abstract fun allRecordsOnDateFlow(date: LocalDate): Flow<List<SexRecordDTO>>

    @Insert
    abstract suspend fun addRecord(profile: SexRecordDTO)

    @Delete
    abstract suspend fun deleteRecord(profile: SexRecordDTO)

    @Update
    abstract suspend fun updateRecord(profile: SexRecordDTO)
}