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
    val types: List<String>,
    val location: String,
    val isProtected: Boolean,
    val hasOrgasm: Boolean,
    val hasPartnerOrgasm: Boolean,
    val experienceRate: Float,
    val note: String?,
)

fun NoteModel.toDTO(): NoteDTO = NoteDTO(
    id = this.id,
    date = this.date,
    types = this.types.map { it.name },
    location = this.location.name,
    isProtected = this.isProtected,
    hasOrgasm = this.hasOrgasm,
    hasPartnerOrgasm = this.hasPartnerOrgasm,
    experienceRate = this.experienceRate,
    note = this.personalNote
)

fun NoteDTO.toModel(): NoteModel = NoteModel(
    id = this.id,
    date = this.date,
    types = this.types.map { PracticeType.valueOf(it) },
    location = PracticeLocation.valueOf(this.location),
    isProtected = this.isProtected,
    hasOrgasm = this.hasOrgasm,
    hasPartnerOrgasm = this.hasPartnerOrgasm,
    experienceRate = this.experienceRate,
    personalNote = this.note.orEmpty()
)