package com.example.data.remote.datasource

import com.example.data.model.Picture
import com.example.data.remote.api.UnsplashAPI
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getUnsplashPhoto(apiKey: String, pageNumber: Int): List<Picture>
}

class RemoteDataSourceImpl @Inject constructor(
    private val api: UnsplashAPI
) : RemoteDataSource {
    override suspend fun getUnsplashPhoto(apiKey: String, pageNumber: Int): List<Picture> {
        val response = api.getPhotoList(page = pageNumber, key = apiKey)
        check(response.isSuccessful)
        return response.body()?.results ?: emptyList()
    }
}