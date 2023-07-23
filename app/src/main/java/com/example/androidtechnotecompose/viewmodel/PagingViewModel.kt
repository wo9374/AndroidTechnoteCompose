package com.example.androidtechnotecompose.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.entity.UnsplashEntity
import com.example.domain.usecase.UnsplashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(
    private val unsplashUseCase: UnsplashUseCase,
) : ViewModel() {
    private val _unsplashList: MutableStateFlow<PagingData<UnsplashEntity>> = MutableStateFlow(value = PagingData.empty())
    val unsplashList get() = _unsplashList

    fun searchKeyword(string: String){
        viewModelScope.launch {
            unsplashUseCase(string).distinctUntilChanged().cachedIn(viewModelScope).collect(){
                _unsplashList.value = it
            }
        }
    }
}