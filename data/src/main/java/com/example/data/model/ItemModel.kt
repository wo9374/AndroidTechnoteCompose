package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * data 레이어에서 사용하는 데이터 모델.
 */
@Entity(tableName = "items")
data class ItemModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val strValue: String
)
