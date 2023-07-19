package com.example.domain.repository

import com.example.domain.entity.UnsplashEntity
import kotlinx.coroutines.flow.Flow

interface UnsplashRepository {
    fun getUnsplashPhoto(page: Int, query: String, perPage: Int): Flow<List<UnsplashEntity>>
}