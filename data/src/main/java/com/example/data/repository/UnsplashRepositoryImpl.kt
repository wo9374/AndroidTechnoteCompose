package com.example.data.repository

import com.example.data.remote.datasource.RemoteDataSource
import com.example.domain.entity.UnsplashEntity
import com.example.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val dataSource: RemoteDataSource //DataSourceModule 에서 Provide 해준 dataSource
) : UnsplashRepository {

    override fun getUnsplashPhoto(
        page: Int, query: String, perPage: Int
    ): Flow<List<UnsplashEntity>> {
        return flow {
            dataSource.getUnsplashPhoto(page, query, perPage)
        }
    }
}