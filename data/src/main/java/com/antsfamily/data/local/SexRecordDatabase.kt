package com.antsfamily.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.antsfamily.data.model.SexRecordDTO

@Database(entities = [SexRecordDTO::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class SexRecordDatabase : RoomDatabase() {
    abstract fun sexRecordDao(): SexRecordDao
}
