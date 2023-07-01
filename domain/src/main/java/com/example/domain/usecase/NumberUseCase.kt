package com.example.domain.usecase

import com.example.domain.entity.NumberEntity
import com.example.domain.repository.NumbersRepository
import kotlinx.coroutines.flow.Flow

class GetNumbersUseCase(private val repository: NumbersRepository){
    operator fun invoke(): Flow<List<NumberEntity>>{
        return repository.getNumbers()
    }
}
class InsertNumberUseCase(private val repository: NumbersRepository) {
    suspend operator fun invoke(value: Int) {
        val number = NumberEntity(id = 0, value = value)
        repository.insertNumber(number)
    }
}

class ClearNumbersUseCase(private val repository: NumbersRepository) {
    suspend operator fun invoke() {
        repository.clearNumbers()
    }
}