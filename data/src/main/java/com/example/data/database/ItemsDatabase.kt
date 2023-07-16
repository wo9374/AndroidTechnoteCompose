package com.example.data.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.data.model.ItemModel
import kotlinx.coroutines.flow.Flow

@Database(entities = [ItemModel::class], version = 1)
abstract class ItemsDatabase : RoomDatabase() {
    abstract fun dao(): ItemsDao
}

@Dao
interface ItemsDao {
    @Query("SELECT * FROM items")
    fun getItems(): Flow<List<ItemModel>> // 쿼리 결과를 바로 Flow Type 으로 return

    @Insert
    suspend fun insertItem(itemModel: ItemModel)

    @Delete
    suspend fun deleteItem(itemModel: ItemModel)

    @Query("DELETE FROM items")
    suspend fun clearItems()
}