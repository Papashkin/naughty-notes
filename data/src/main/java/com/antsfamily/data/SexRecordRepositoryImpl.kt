package com.antsfamily.data

import com.antsfamily.data.local.SexRecordDao
import javax.inject.Inject

class SexRecordRepositoryImpl @Inject constructor(
    private val dao: SexRecordDao
): SexRecordRepository {
    override suspend fun getData() {
        TODO("Not yet implemented")
    }

    override suspend fun updateData() {
        TODO("Not yet implemented")
    }

    override suspend fun saveData() {
        TODO("Not yet implemented")
    }
}