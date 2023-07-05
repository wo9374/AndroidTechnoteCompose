package com.example.androidtechnotecompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.ClearItemsUseCase
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
    private val clearItemsUseCase: ClearItemsUseCase,
) : ViewModel() {

    val numbers = getItemsUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertItem(str: String) = viewModelScope.launch {
        insertItemUseCase(str)
    }

    fun clearItems() = viewModelScope.launch {
        clearItemsUseCase()
    }
}