package com.antsfamily.data.di

import android.content.Context
import androidx.room.Room
import com.antsfamily.data.local.SexRecordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideBikeTrainerDatabase(@ApplicationContext appContext: Context): SexRecordDatabase =
        Room.databaseBuilder(appContext, SexRecordDatabase::class.java, DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideSexRecordDao(database: SexRecordDatabase) = database.sexRecordDao()

    private const val DATABASE_NAME = "Naughty Notes DB"
}
