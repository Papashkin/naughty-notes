package com.antsfamily.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SexRecordDTO(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: String,
    val type: String,
    val isProtected: Boolean,
    val pleasureRate: Int,
    val painRate: Int,
    val note: String,
)