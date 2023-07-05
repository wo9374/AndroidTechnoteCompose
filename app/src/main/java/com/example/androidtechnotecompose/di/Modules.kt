package com.example.androidtechnotecompose.di

import com.example.data.datasource.LocalDataSourceImpl
import com.example.data.repository.ItemRepositoryImpl
import com.example.domain.repository.ItemsRepository
import com.example.domain.usecase.ClearItemsUseCase
import com.example.domain.usecase.GetItemsUseCase
import com.example.domain.usecase.InsertItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetItemsUseCase(repository: ItemsRepository) = GetItemsUseCase(repository)

    @Provides
    fun provideInsertItemUseCase(repository: ItemsRepository) = InsertItemUseCase(repository)

    @Provides
    fun provideClearItemsUseCase(repository: ItemsRepository) = ClearItemsUseCase(repository)
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule{

    @Provides
    @Singleton
    fun provideItemRepository(localDataSourceImpl: LocalDataSourceImpl): ItemsRepository = ItemRepositoryImpl(localDataSourceImpl)
}