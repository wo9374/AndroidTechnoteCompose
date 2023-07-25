package com.example.data.model

import com.example.domain.entity.ItemEntity
import com.example.domain.entity.UnsplashEntity

/**
 * data layer 에서 사용할 data model 인 [ItemModel]을
 * domain layer 에 정의된 entity [ItemEntity]로 변환
 */
fun ItemModel.toEntity(): ItemEntity = ItemEntity(id = this.id, strValue = this.strValue)

/**
 * domain layer 에 정의된 있는 entity [ItemEntity]를
 * data layer 에서 사용할 data model 인 [ItemModel]로 변환
 */
fun ItemEntity.toModel(): ItemModel = ItemModel(id = this.id, strValue = this.strValue)

fun Picture.toEntity(): UnsplashEntity = UnsplashEntity(
    id = this.id,
    thumbnailUrl = this.pictureUrl.thumbUrl,
    fullSizeUrl = this.pictureUrl.fullUrl,
)

fun UnsplashEntity.toPicture(): Picture = Picture(
    id = this.id,
    pictureUrl = PictureUrl(
        this.thumbnailUrl,
        this.fullSizeUrl,
    )
)