package com.example.domain.repository

import com.example.domain.entity.ItemEntity
import kotlinx.coroutines.flow.Flow

/**
 * 해당 interface 의 구현 class 는 DataLayer 에 존재함
 */
interface ItemsRepository {
    fun getItems(): Flow<List<ItemEntity>>
    suspend fun insertItem(itemEntity: ItemEntity)

    suspend fun deleteItem(itemEntity: ItemEntity)
    suspend fun clearItems()
}