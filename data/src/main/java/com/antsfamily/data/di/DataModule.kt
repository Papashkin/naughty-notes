package com.antsfamily.data.di

import android.content.Context
import androidx.room.Room
import com.antsfamily.data.local.NotesDatabase
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
    fun provideBikeTrainerDatabase(@ApplicationContext appContext: Context): NotesDatabase =
        Room.databaseBuilder(appContext, NotesDatabase::class.java, DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideSexRecordDao(database: NotesDatabase) = database.sexRecordDao()

    private const val DATABASE_NAME = "Naughty Notes DB"
}
