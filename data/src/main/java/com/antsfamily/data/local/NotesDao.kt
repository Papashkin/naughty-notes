package com.antsfamily.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.antsfamily.data.model.NoteDTO
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
abstract class NotesDao {

    @Query("SELECT * from notedto")
    abstract suspend fun getNotes(): List<NoteDTO>

    @Query("SELECT * from notedto")
    abstract fun getNotesFlow(): Flow<List<NoteDTO>>

    @Query("Select * from notedto where id = :id")
    abstract suspend fun getNoteById(id: Int): NoteDTO?

    @Query("SELECT * from notedto where date = :date")
    abstract fun getNotesOnDateFlow(date: LocalDate): Flow<List<NoteDTO>>

    @Insert
    abstract suspend fun addNote(profile: NoteDTO)

    @Delete
    abstract suspend fun deleteNote(profile: NoteDTO)

    @Update
    abstract suspend fun updateNote(profile: NoteDTO)
}