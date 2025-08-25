package com.antsfamily.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.antsfamily.data.model.NoteDTO

@Database(entities = [NoteDTO::class], version = 2, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao
}

val Migration_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE notedto ADD COLUMN hasPartnerOrgasm INTEGER NOT NULL DEFAULT 0")
    }
}