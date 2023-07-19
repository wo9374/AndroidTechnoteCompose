package com.example.data.di

import com.example.data.local.datasource.LocalDataSourceImpl
import com.example.data.remote.datasource.RemoteDataSourceImpl
import com.example.data.repository.ItemRepositoryImpl
import com.example.data.repository.UnsplashRepositoryImpl
import com.example.domain.repository.ItemsRepository
import com.example.domain.repository.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt로 Repository관련 의존성 주입을 해주기 위한 모듈
 * */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideItemRepository(localDataSourceImpl: LocalDataSourceImpl): ItemsRepository =
        ItemRepositoryImpl(localDataSourceImpl)

    @Provides
    @Singleton
    fun provideUnsplashRepositoryImpl(remoteDataSourceImpl: RemoteDataSourceImpl): UnsplashRepository =
        UnsplashRepositoryImpl(remoteDataSourceImpl)

}