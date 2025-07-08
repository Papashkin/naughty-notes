package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SexRecordDTO(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: String,
    val types: List<SexType>,
    val isWithCondom: Boolean
)

enum class SexType {
    VAGINAL,
    ANAL,
    ORAL,
}