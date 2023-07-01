package com.example.data.di

import com.example.data.database.NumbersDao
import com.example.data.datasource.LocalDataSource
import com.example.data.datasource.LocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Provides
    @Singleton
    fun provideLocalDataSource(dao: NumbersDao): LocalDataSource = LocalDataSourceImpl(dao)
}