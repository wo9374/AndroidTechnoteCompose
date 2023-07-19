package com.example.data.local.datasource

import com.example.data.database.ItemsDao
import com.example.data.model.ItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun getItems(): Flow<List<ItemModel>>
    suspend fun insertItem(itemModel: ItemModel)
    suspend fun deleteItem(itemModel: ItemModel)
    suspend fun clearItems()
}

class LocalDataSourceImpl @Inject constructor(private val dao: ItemsDao) : LocalDataSource {

    override fun getItems(): Flow<List<ItemModel>> {
        return dao.getItems()
    }

    override suspend fun insertItem(itemModel: ItemModel) {
        dao.insertItem(itemModel)
    }

    override suspend fun deleteItem(itemModel: ItemModel) {
        dao.deleteItem(itemModel)
    }

    override suspend fun clearItems() {
        dao.clearItems()
    }
}