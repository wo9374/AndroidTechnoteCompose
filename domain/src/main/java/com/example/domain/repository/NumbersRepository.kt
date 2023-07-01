package com.example.domain.repository

import com.example.domain.entity.NumberEntity
import kotlinx.coroutines.flow.Flow

/**
 * 해당 interface 의 구현 class 는 DataLayer 에 존재함
 */
interface NumbersRepository {
    fun getNumbers(): Flow<List<NumberEntity>>
    suspend fun insertNumber(numberEntity: NumberEntity)
    suspend fun clearNumbers()
}