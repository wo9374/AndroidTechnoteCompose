package com.example.domain.usecase

import com.example.domain.entity.ItemEntity
import com.example.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow

class GetItemsUseCase(private val repository: ItemsRepository){
    operator fun invoke(): Flow<List<ItemEntity>>{
        return repository.getItems()
    }
}
class InsertItemUseCase(private val repository: ItemsRepository) {
    suspend operator fun invoke(str: String) {
        val number = ItemEntity(id = 0, strValue = str)
        repository.insertItem(number)
    }
}

class ClearItemsUseCase(private val repository: ItemsRepository) {
    suspend operator fun invoke() {
        repository.clearItems()
    }
}