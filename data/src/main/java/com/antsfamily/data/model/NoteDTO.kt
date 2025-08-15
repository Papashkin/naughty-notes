package com.antsfamily.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.antsfamily.domain.model.NoteModel
import java.time.LocalDate

@Entity
data class NoteDTO(
    @PrimaryKey val id: Int,
    val date: LocalDate,
    val type: String,
    val isProtected: Boolean,
    val pleasureRate: Int,
    val painRate: Int,
    val note: String?,
)

fun NoteModel.toDTO(): NoteDTO = NoteDTO(
    id = this.id,
    date = this.date,
    type = this.type.name,
    isProtected = this.isProtected,
    pleasureRate = this.rate,
    painRate = this.painRate,
    note = this.personalNote
)