package local

import androidx.room.RoomDatabase

//@Database(entities = [ProfileDTO::class, WorkoutDTO::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class SexRecordDatabase : RoomDatabase() {
    abstract fun sexRecordDao(): SexRecordDao
}
