@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.androidtechnotecompose.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
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
    val pagingItems: LazyPagingItems<UnsplashEntity> =
        viewModel.unsplashList.collectAsLazyPagingItems()

    val itemHeight = 100.dp


    //loading 분기 https://betterprogramming.pub/turn-the-page-overview-of-android-paging3-library-integration-with-jetpack-compose-3a7881ed75b4
    when (pagingItems.loadState.refresh) {
        is LoadState.Error -> {

        }

        is LoadState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Pagination Loading")
                CircularProgressIndicator(color = Color.Black)
            }
        }

        else -> {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = itemHeight),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Log.d("PagingScreen", "Items : ${pagingItems.itemSnapshotList.items}")

                items(pagingItems.itemCount) { index ->
                    PhotoCard(
                        entity = pagingItems[index]!!,
                        squareHeight = itemHeight
                    ) {
                        //onClicked
                    }
                }
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