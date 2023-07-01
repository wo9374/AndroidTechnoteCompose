package com.example.data.model

import com.example.domain.entity.NumberEntity

/**
 * data layer 에서 사용할 data model 인 [NumberModel]을
 * domain layer 에 정의된 entity [NumberEntity]로 변환
 */
fun NumberModel.toEntity(): NumberEntity = NumberEntity(id = this.id, value = this.value)

/**
 * domain layer 에 정의된 있는 entity [NumberEntity]를
 * data layer 에서 사용할 data model 인 [NumberModel]로 변환
 */
fun NumberEntity.toModel(): NumberModel = NumberModel(value = this.value)