package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.entity.UnsplashEntity
import kotlinx.coroutines.flow.Flow

interface UnsplashRepository {

    suspend fun getUnsplashPhoto(searchKeyword: String): Flow<PagingData<UnsplashEntity>>

}