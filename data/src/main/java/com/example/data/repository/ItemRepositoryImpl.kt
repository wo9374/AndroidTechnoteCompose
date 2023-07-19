package com.example.data.repository

import com.example.data.local.datasource.LocalDataSource
import com.example.data.model.ItemModel
import com.example.data.model.toEntity
import com.example.data.model.toModel
import com.example.domain.entity.ItemEntity
import com.example.domain.repository.ItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * domain layer 에 정의한 [ItemsRepository] interface 의 구현 클래스.
 * Repository 는 다양한 DataSource 를 상황에 맞게 사용하여 UseCase 의 요청을 처리할 수 있어야 함
 */
class ItemRepositoryImpl @Inject constructor(private val dataSource: LocalDataSource) :
    ItemsRepository {

    override fun getItems(): Flow<List<ItemEntity>> {
        return dataSource.getItems().map { entityList: List<ItemModel> ->
            entityList.map { element -> element.toEntity() }
        }
        // DataSource 에서 Flow<List<ItemEntity>>의 형태의 Data 를 domain layer 에서 사용할 수 있게 Flow<List<ItemModel>>로 변환
    }

    override suspend fun insertItem(itemEntity: ItemEntity) {
        val itemModel = itemEntity.toModel()
        dataSource.insertItem(itemModel)
        // domain layer 의 UseCase 에서 파라미터로 넘겨준 ItemEntity Data 를 Database 에서 사용할 ItemModel 로 변환 insert 호출
    }

    override suspend fun deleteItem(itemEntity: ItemEntity) {
        val itemModel = itemEntity.toModel()
        dataSource.deleteItem(itemModel)
    }

    override suspend fun clearItems() {
        dataSource.clearItems()
    }
}