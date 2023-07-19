package com.example.data.remote.api

import com.example.data.BuildConfig
import com.example.data.model.UnsplashModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashAPI {

    @GET(ApiInfo.UNSPLASH_END_POINT)
    suspend fun getPhotoList(
        @Query("page") page: Int,
        @Query("query") query: String,    //검색 키워드
        @Query("client_id") key: String = BuildConfig.UNSPLASH_ACCESS_KEY,    //Key 값
        @Query("per_page") perPage: Int   //Page Size
    ): Response<UnsplashModel>
}