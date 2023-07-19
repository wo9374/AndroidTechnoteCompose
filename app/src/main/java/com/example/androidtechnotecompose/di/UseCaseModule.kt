package com.example.androidtechnotecompose.di

import com.example.domain.repository.ItemsRepository
import com.example.domain.repository.UnsplashRepository
import com.example.domain.usecase.ClearItemsUseCase
import com.example.domain.usecase.DeleteItemUseCase
import com.example.domain.usecase.GetItemsUseCase
import com.example.domain.usecase.InsertItemUseCase
import com.example.domain.usecase.UnsplashUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideGetItemsUseCase(repository: ItemsRepository) = GetItemsUseCase(repository)

    @Provides
    fun provideInsertItemUseCase(repository: ItemsRepository) = InsertItemUseCase(repository)

    @Provides
    fun provideDeleteItemUseCase(repository: ItemsRepository) = DeleteItemUseCase(repository)

    @Provides
    fun provideClearItemsUseCase(repository: ItemsRepository) = ClearItemsUseCase(repository)

    @Provides
    fun provideUnsplashUseCase(unsplashRepository: UnsplashRepository) = UnsplashUseCase(unsplashRepository)
}