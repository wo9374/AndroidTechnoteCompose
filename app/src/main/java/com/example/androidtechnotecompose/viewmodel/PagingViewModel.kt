package com.example.androidtechnotecompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.remote.datasource.UnsplashPagingSource
import com.example.domain.entity.UnsplashEntity
import com.example.domain.usecase.UnsplashUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(
    private val unsplashUseCase: UnsplashUseCase,
) : ViewModel() {

    private val _unsplashList: MutableStateFlow<PagingData<UnsplashEntity>> = MutableStateFlow(value = PagingData.empty())
    val unsplashList: MutableStateFlow<PagingData<UnsplashEntity>> get() = _unsplashList

    init {
        viewModelScope.launch {
            unsplashUseCase().distinctUntilChanged().cachedIn(viewModelScope).collect {
                _unsplashList.value = it
            }
        }
    }
}