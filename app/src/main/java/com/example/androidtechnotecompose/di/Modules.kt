package com.example.androidtechnotecompose.di

import android.content.Context
import com.example.data.datasource.LocalDataSourceImpl
import com.example.data.repository.NumberRepositoryImpl
import com.example.domain.repository.NumbersRepository
import com.example.domain.usecase.ClearNumbersUseCase
import com.example.domain.usecase.GetNumbersUseCase
import com.example.domain.usecase.InsertNumberUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetNumbersUseCase(repository: NumbersRepository) = GetNumbersUseCase(repository)

    @Provides
    fun provideInsertNumberUseCase(repository: NumbersRepository) = InsertNumberUseCase(repository)

    @Provides
    fun provideClearNumbersUseCase(repository: NumbersRepository) = ClearNumbersUseCase(repository)
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule{

    @Provides
    @Singleton
    fun provideNumberRepository(localDataSourceImpl: LocalDataSourceImpl): NumbersRepository = NumberRepositoryImpl(localDataSourceImpl)
}