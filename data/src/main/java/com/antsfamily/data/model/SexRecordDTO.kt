package com.antsfamily.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.antsfamily.domain.model.NoteModel
import java.time.LocalDate
import kotlin.random.Random

@Entity
data class SexRecordDTO(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: LocalDate,
    val type: String,
    val isProtected: Boolean,
    val pleasureRate: Int,
    val painRate: Int,
    val note: String?,
)

fun NoteModel.toDTO(): SexRecordDTO = SexRecordDTO(
        id = Random.nextInt(),
        date = this.date,
        type = this.type.name,
        isProtected = this.isProtected,
        pleasureRate = this.rate,
        painRate = this.painRate,
        note = this.personalNote
    )