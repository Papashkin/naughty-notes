package com.antsfamily.data.di

import android.content.Context
import androidx.room.Room
import com.antsfamily.data.local.NotesDatabase
import com.antsfamily.data.local.SharedPrefs
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
    fun provideNotesDatabase(@ApplicationContext appContext: Context): NotesDatabase =
        Room.databaseBuilder(appContext, NotesDatabase::class.java, DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideNotesDao(database: NotesDatabase) = database.NotesDao()

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext appContext: Context): SharedPrefs =
        SharedPrefs(appContext)

    private const val DATABASE_NAME = "Naughty Notes DB"
}
