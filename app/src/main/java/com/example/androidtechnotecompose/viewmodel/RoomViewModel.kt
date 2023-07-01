package com.example.androidtechnotecompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.ClearNumbersUseCase
import com.example.domain.usecase.GetNumbersUseCase
import com.example.domain.usecase.InsertNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val getNumbersUseCase: GetNumbersUseCase,
    private val insertNumberUseCase: InsertNumberUseCase,
    private val clearNumbersUseCase: ClearNumbersUseCase,
) : ViewModel() {

    val numbers = getNumbersUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertNumber(value: Int) = viewModelScope.launch {
        insertNumberUseCase(value)
    }

    fun clearNumbers() = viewModelScope.launch {
        clearNumbersUseCase()
    }
}