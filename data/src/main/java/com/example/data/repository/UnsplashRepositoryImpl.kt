package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.remote.datasource.RemoteDataSource
import com.example.data.remote.datasource.UnsplashPagingSource
import com.example.domain.entity.UnsplashEntity
import com.example.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource //DataSourceModule 에서 Provide 해준 dataSource
) : UnsplashRepository {
    override suspend fun getUnsplashPhoto(): Flow<PagingData<UnsplashEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = {
                UnsplashPagingSource(dataSource)
            }
        ).flow
    }
}