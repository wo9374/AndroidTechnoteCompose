package com.example.data.di

import com.example.data.remote.api.ApiInfo
import com.example.data.remote.api.UnsplashAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideUnsplashRetrofit(
        okHttpClient: OkHttpClient,                     //Provide OkHttpClient
        gsonConverterFactory: GsonConverterFactory      //Provide GsonFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiInfo.UNSPLASH_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideUnsplashService(retrofit: Retrofit): UnsplashAPI {
        return retrofit.create(UnsplashAPI::class.java)
    }
}