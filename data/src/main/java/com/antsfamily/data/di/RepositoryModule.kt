package com.antsfamily.data.di

import com.antsfamily.data.SexRecordRepositoryImpl
import com.antsfamily.domain.SexRecordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsSexRecordRepository(repositoryImpl: SexRecordRepositoryImpl): SexRecordRepository
}
