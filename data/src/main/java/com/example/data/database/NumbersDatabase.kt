package com.example.data.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.data.model.NumberModel
import kotlinx.coroutines.flow.Flow

@Database(entities = [NumberModel::class], version = 1)
abstract class NumbersDatabase : RoomDatabase(){
    abstract fun dao(): NumbersDao
}

@Dao
interface NumbersDao{
    @Query("SELECT * FROM numbers")
    fun getNumbers(): Flow<List<NumberModel>> // 쿼리 결과를 바로 Flow Type 으로 return

    @Insert
    suspend fun insertNumber(numberModel: NumberModel)

    @Query("DELETE FROM numbers")
    suspend fun clearNumbers()
}