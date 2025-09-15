package com.antsfamily.data.di

import android.content.Context
import androidx.room.Room
import com.antsfamily.data.local.AppVersionSource
import com.antsfamily.data.local.EncryptedSharedPrefers
import com.antsfamily.data.local.Migration_1_2
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
            .addMigrations(Migration_1_2)
            .build()

    @Singleton
    @Provides
    fun provideNotesDao(database: NotesDatabase) = database.NotesDao()

    @Singleton
    @Provides
    fun provideSharedPrefs(@ApplicationContext appContext: Context): SharedPrefs =
        SharedPrefs(appContext)

    @Singleton
    @Provides
    fun provideEncryptedSharedPrefs(@ApplicationContext appContext: Context): EncryptedSharedPrefers =
        EncryptedSharedPrefers(appContext)

    @Singleton
    @Provides
    fun provideAppVersionSource(@ApplicationContext context: Context) =
        AppVersionSource(context = context)

    private const val DATABASE_NAME = "Naughty Notes DB"
}
