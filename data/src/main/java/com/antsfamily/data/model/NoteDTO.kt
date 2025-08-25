package com.antsfamily.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import java.time.LocalDate

@Entity
data class NoteDTO(
    @PrimaryKey val id: Int,
    val date: LocalDate,
    val type: String,
    val location: String,
    val isProtected: Boolean,
    val hasOrgasm: Boolean,
    val hasPartnerOrgasm: Boolean,
    val pleasureRate: Int,
    val painRate: Int,
    val note: String?,
)

fun NoteModel.toDTO(): NoteDTO = NoteDTO(
    id = this.id,
    date = this.date,
    type = this.type.name,
    location = this.location.name,
    isProtected = this.isProtected,
    hasOrgasm = this.hasOrgasm,
    hasPartnerOrgasm = this.hasPartnerOrgasm,
    pleasureRate = this.rate,
    painRate = this.painRate,
    note = this.personalNote
)

fun NoteDTO.toModel(): NoteModel = NoteModel(
    id = this.id,
    date = this.date,
    type = PracticeType.valueOf(this.type),
    location = PracticeLocation.valueOf(this.location),
    isProtected = this.isProtected,
    hasOrgasm = this.hasOrgasm,
    hasPartnerOrgasm = this.hasPartnerOrgasm,
    rate = this.pleasureRate,
    painRate = this.painRate,
    personalNote = this.note.orEmpty()
)