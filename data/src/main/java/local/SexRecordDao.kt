package local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import model.SexRecordDTO

@Dao
abstract class SexRecordDao {

    @Query("SELECT * from sexrecorddto")
    abstract suspend fun getAll(): List<SexRecordDTO>

    @Query("Select * from sexrecorddto where id = :id")
    abstract suspend fun getProfile(id: Int): SexRecordDTO?

    @Insert
    abstract suspend fun addProfile(profile: SexRecordDTO)

    @Update
    abstract suspend fun updateProfile(profile: SexRecordDTO)
}