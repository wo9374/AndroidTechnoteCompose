package com.example.data.remote.datasource

import com.example.data.model.Picture
import com.example.data.remote.api.UnsplashAPI
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun getUnsplashPhoto(pageNumber: Int, searchKeyword: String, apiKey: String ): List<Picture>
}

class RemoteDataSourceImpl @Inject constructor(
    private val api: UnsplashAPI
) : RemoteDataSource {
    override suspend fun getUnsplashPhoto(pageNumber: Int, searchKeyword: String, apiKey: String): List<Picture> {
        return try {
            val response = api.getPhotoList(page = pageNumber, query = searchKeyword, key = apiKey)
            check(response.isSuccessful)
            response.body()?.results ?: emptyList()
        } catch (e: Exception){
            e.printStackTrace()
            emptyList<Picture>()
        }
    }
}