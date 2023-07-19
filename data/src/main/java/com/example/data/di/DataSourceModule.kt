package com.example.data.di

import com.example.data.database.ItemsDao
import com.example.data.local.datasource.LocalDataSource
import com.example.data.local.datasource.LocalDataSourceImpl
import com.example.data.remote.api.UnsplashAPI
import com.example.data.remote.datasource.RemoteDataSource
import com.example.data.remote.datasource.RemoteDataSourceImpl
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
    fun provideLocalDataSource(dao: ItemsDao): LocalDataSource = LocalDataSourceImpl(dao)

    @Provides
    @Singleton
    fun provideRemoteDataSource(unsplashAPI: UnsplashAPI): RemoteDataSource =
        RemoteDataSourceImpl(unsplashAPI)
    //NetworkModule 에서 Provide 해준 unsplashAPI
}