package com.example.domain.usecase

import com.example.domain.entity.UnsplashEntity
import com.example.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow

class UnsplashUseCase(private val unsplashRepository: UnsplashRepository) {
    operator fun invoke(page: Int, query: String, perPage: Int): Flow<List<UnsplashEntity>> =
        unsplashRepository.getUnsplashPhoto(page, query, perPage)
}