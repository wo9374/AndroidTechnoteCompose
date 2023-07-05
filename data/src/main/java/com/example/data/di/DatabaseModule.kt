package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.ItemsDao
import com.example.data.database.ItemsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule{
    /**
     * Dao instance 생성 명시
     * */
    @Provides
    @Singleton
    fun provideLocalClinicDao(database: ItemsDatabase): ItemsDao = database.dao()

    /**
     * Database instance 생성 명시
     * */
    @Provides
    @Singleton
    fun provideClinicDatabase(@ApplicationContext appContext: Context): ItemsDatabase =
        Room.databaseBuilder(appContext, ItemsDatabase::class.java, "item_room").build()
}