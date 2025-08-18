package com.antsfamily.data.di

import com.antsfamily.data.NoteRepositoryImpl
import com.antsfamily.data.SettingsRepositoryImpl
import com.antsfamily.domain.repository.NoteRepository
import com.antsfamily.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsNoteRepository(repositoryImpl: NoteRepositoryImpl): NoteRepository

    @Binds
    abstract fun bindsSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository
}
