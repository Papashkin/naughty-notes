package com.antsfamily.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.antsfamily.data.model.NoteDTO

@Database(entities = [NoteDTO::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun NotesDao(): NotesDao
}
