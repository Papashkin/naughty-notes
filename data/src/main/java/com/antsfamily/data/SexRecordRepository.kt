package com.antsfamily.data

interface SexRecordRepository {
    suspend fun getData()
    suspend fun updateData()
    suspend fun saveData()
}