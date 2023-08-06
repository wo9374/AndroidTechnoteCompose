package com.example.androidtechnotecompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.ItemEntity
import com.example.domain.usecase.ClearItemsUseCase
import com.example.domain.usecase.DeleteItemUseCase
import com.example.domain.usecase.GetItemsUseCase
import com.example.domain.usecase.InsertItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase,
    private val insertItemUseCase: InsertItemUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val clearItemsUseCase: ClearItemsUseCase,
) : ViewModel() {

    val numbers = getItemsUseCase().stateIn( //Flow를 StateFlow 로 변환
        scope = viewModelScope,              //StateFlow가 Flow로부터 데이터를 구독받을 CoroutineScope 명시
        started = SharingStarted.WhileSubscribed(5000), //Flow로부터 언제부터 구독을 할지 명시
        initialValue = emptyList()           //초기값
    )

    fun insertItem(str: String) = viewModelScope.launch {
        insertItemUseCase(str)
    }

    fun deleteItem(itemEntity: ItemEntity) = viewModelScope.launch {
        deleteItemUseCase(itemEntity)
    }

    fun clearItems() = viewModelScope.launch {
        clearItemsUseCase()
    }
}