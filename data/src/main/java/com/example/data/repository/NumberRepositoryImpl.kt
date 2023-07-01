package com.example.data.repository

import com.example.data.datasource.LocalDataSource
import com.example.data.model.NumberModel
import com.example.data.model.toEntity
import com.example.data.model.toModel
import com.example.domain.entity.NumberEntity
import com.example.domain.repository.NumbersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * domain layer 에 정의한 [NumbersRepository] interface 의 구현 클래스.
 * Repository 는 다양한 DataSource 를 상황에 맞게 사용하여 UseCase 의 요청을 처리할 수 있어야 함
 */
class NumberRepositoryImpl @Inject constructor(private val dataSource: LocalDataSource) : NumbersRepository {

    override fun getNumbers(): Flow<List<NumberEntity>> {
        return dataSource.getNumbers().map { entityList: List<NumberModel> ->
            entityList.map { element -> element.toEntity() }
        }
        // DataSource 에서 Flow<List<NumberEntity>>의 형태의 Data 를 domain layer 에서 사용할 수 있게 Flow<List<NumberModel>>로 변환
    }

    override suspend fun insertNumber(numberEntity: NumberEntity) {
        val numberModel = numberEntity.toModel()
        dataSource.insertNumber(numberModel)
        // domain layer 의 UseCase 에서 파라미터로 넘겨준 NumberEntity Data 를 Database 에서 사용할 NumberModel 로 변환 insert 호출
    }

    override suspend fun clearNumbers() {
        dataSource.clearNumbers()
    }
}