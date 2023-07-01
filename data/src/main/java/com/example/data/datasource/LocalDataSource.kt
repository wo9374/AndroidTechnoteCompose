package com.example.data.datasource

import com.example.data.database.NumbersDao
import com.example.data.model.NumberModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalDataSource {
    fun getNumbers(): Flow<List<NumberModel>>
    suspend fun insertNumber(numberModel: NumberModel)
    suspend fun clearNumbers()
}

class LocalDataSourceImpl @Inject constructor(private val dao: NumbersDao): LocalDataSource{

    override fun getNumbers(): Flow<List<NumberModel>> {
        return dao.getNumbers()
    }

    override suspend fun insertNumber(numberModel: NumberModel) {
        dao.insertNumber(numberModel)
    }

    override suspend fun clearNumbers() {
        dao.clearNumbers()
    }
}