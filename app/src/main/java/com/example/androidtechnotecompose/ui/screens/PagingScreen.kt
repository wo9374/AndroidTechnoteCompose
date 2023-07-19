@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.androidtechnotecompose.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.androidtechnotecompose.viewmodel.PagingViewModel
import com.example.domain.entity.UnsplashEntity

@Composable
fun PagingScreen(
    viewModel: PagingViewModel = hiltViewModel()
) {
    val unsplashPagingItems: LazyPagingItems<UnsplashEntity> = viewModel.unsplashList.collectAsLazyPagingItems()

    val itemHeight = 100.dp

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = itemHeight),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Log.d("PagingScreen", "Items : ${unsplashPagingItems.itemSnapshotList.items}")

        items(unsplashPagingItems.itemCount) { index ->

            PhotoCard(
                entity = unsplashPagingItems[index]!!,
                squareHeight = itemHeight
            ) {
                //onClicked
            }
        }
    }

    /**
     * GridCells.Adaptive(minSize = 128.dp)
     * GridCells.Fixed( int )
     * */
}

@Composable
fun PhotoCard(
    entity: UnsplashEntity,
    squareHeight: Dp,
    onClick: () -> Unit
) {
    GlideImage(
        modifier = Modifier
            .height(squareHeight)
            .clickable(onClick = onClick),
        model = entity.urlStr,
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}