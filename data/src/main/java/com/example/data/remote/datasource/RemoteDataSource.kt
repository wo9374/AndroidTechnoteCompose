package com.example.data.remote.datasource

import com.example.data.model.UnsplashModel
import com.example.data.remote.api.UnsplashAPI
import retrofit2.Response
import javax.inject.Inject

//로드에 사용되는 초기 키
private const val STARTING_KEY = 0
private const val LOAD_DELAY_MILLIS = 3_000L

interface RemoteDataSource {
    suspend fun getUnsplashPhoto(page: Int, query: String, perPage: Int): Response<UnsplashModel>
}

class RemoteDataSourceImpl @Inject constructor(
    private val unsplashAPI: UnsplashAPI
) : RemoteDataSource {

    override suspend fun getUnsplashPhoto(page: Int, query: String, perPage: Int): Response<UnsplashModel> =
        unsplashAPI.getPhotoList(page, query, perPage = perPage)
}