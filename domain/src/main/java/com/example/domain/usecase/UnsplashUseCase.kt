package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.entity.UnsplashEntity
import com.example.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow

class UnsplashUseCase(private val unsplashRepository: UnsplashRepository) {
    suspend operator fun invoke(): Flow<PagingData<UnsplashEntity>> = unsplashRepository.getUnsplashPhoto()
}