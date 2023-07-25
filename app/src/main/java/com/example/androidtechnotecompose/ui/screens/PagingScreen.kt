@file:OptIn(
    ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class
)

package com.example.androidtechnotecompose.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.androidtechnotecompose.R
import com.example.androidtechnotecompose.viewmodel.PagingViewModel
import com.example.domain.entity.UnsplashEntity
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PagingScreen(
    pagingViewModel: PagingViewModel = hiltViewModel()
) {

    val localContext = LocalContext.current

    val topBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        topBar = {
            OutLineSearchBar(
                context = localContext,
                scrollBehavior = topBarScrollBehavior,
                viewModel = pagingViewModel
            )
        },
    ) { innerPadding ->

        val pagingItems: LazyPagingItems<UnsplashEntity> =
            pagingViewModel.unsplashList.collectAsLazyPagingItems()

        when (pagingItems.loadState.refresh) {
            is LoadState.Error -> {
                Log.d("Paging LoadState.Error", "${pagingItems.loadState}")
            }

            is LoadState.Loading -> {
                LoadingPhotoDisplay(innerPadding)
            }

            else -> {
                //List 가 비었을 때 예외 처리
                if (pagingItems.itemSnapshotList.isEmpty()) {
                    EmptyResultDisplay(innerPadding)
                } else {

                    /*VerticalGridDisplay(
                        context = localContext,
                        items = pagingItems,
                        appBarPadding = innerPadding,
                    )*/

                    SelectPhotoDisplay(
                        context = localContext,
                        items = pagingItems,
                        appBarPadding = innerPadding
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingPhotoDisplay(
    appBarPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(appBarPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = stringResource(R.string.pagination_loading))

        Spacer(modifier = Modifier.height(4.dp))

        CircularProgressIndicator()
    }
}

@Composable
fun SelectPhotoDisplay(
    context: Context,
    items: LazyPagingItems<UnsplashEntity>,
    appBarPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(appBarPadding)
    ) {

        var selectedItem by remember { mutableStateOf(items[0]) }

        LazyRow(
            modifier = Modifier
                .wrapContentHeight()
                .padding(vertical = 10.dp),
        ) {

            items(items.itemCount) { index ->
                SkyDoveGlide(
                    context = context,
                    entity = items[index]!!,
                    modifier = Modifier
                        .width(80.dp)
                        .height(60.dp)
                        .padding(horizontal = 5.dp)
                        .clickable(onClick = {
                            selectedItem = items[index]
                        }),
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ShimmerGlide(
                context = context,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(unbounded = true),
                url = selectedItem!!.fullSizeUrl,
                contentScale = ContentScale.Fit
            )

            CircularGlide(
                context = context,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(unbounded = true),
                url = selectedItem!!.fullSizeUrl,
                contentScale = ContentScale.Fit,
            )
        }
    }
}

@Composable
fun VerticalGridDisplay(
    context: Context,
    items: LazyPagingItems<UnsplashEntity>,
    appBarPadding: PaddingValues,
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(appBarPadding),
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {

        items(items.itemCount) { index ->
            SkyDoveGlide(
                context = context,
                entity = items[index]!!,
                modifier = Modifier
                    .height(150.dp)
                    .clickable(onClick = {

                    })
            )
        }

    }
    /**
     * GridCells.Adaptive(minSize = 128.dp)
     * GridCells.Fixed( int )
     * */
}

@Composable
fun EmptyResultDisplay(
    appBarPadding: PaddingValues
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(appBarPadding)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.no_search_result)
        )
    }
}


@Composable
fun OutLineSearchBar(
    context: Context,
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: PagingViewModel
) {
    var keyword by remember { mutableStateOf("Android") }

    val textFieldTemp = remember { mutableStateOf("Android") }
    val emptySearchTxt = stringResource(R.string.search_image)

    //searchTxt 가 변할때 마다 검색
    LaunchedEffect(key1 = keyword) {
        viewModel.searchKeyword(keyword)
    }

    TopAppBar(
        modifier = Modifier.background(MaterialTheme.colorScheme.primary),
        title = {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(end = 18.dp),
                value = textFieldTemp.value,
                onValueChange = {
                    textFieldTemp.value = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = androidx.compose.material.MaterialTheme.colors.primary
                    )
                },
                placeholder = {
                    Text(
                        fontSize = 16.sp,
                        text = emptySearchTxt
                    )
                },
                colors = TextFieldDefaults.textFieldColors(textColor = Color.LightGray),
                keyboardOptions = KeyboardOptions.Default.copy(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    if (textFieldTemp.value.isNotEmpty()) {
                        keyword = textFieldTemp.value
                    } else {
                        Toast.makeText(context, emptySearchTxt, Toast.LENGTH_SHORT).show()
                    }
                }),
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            scrolledContainerColor = MaterialTheme.colorScheme.background,
        ),
        scrollBehavior = scrollBehavior,
    )
}


@Composable
fun SkyDoveGlide(
    context: Context,
    entity: UnsplashEntity,
    modifier: Modifier,
) {
    GlideImage(
        imageModel = entity.thumbnailUrl,
        modifier = modifier.clip(RoundedCornerShape(6.dp)),
        requestBuilder = {
            Glide
                .with(context)
                .asDrawable()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .sizeMultiplier(0.1f)
        },
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        loading = {
            Box(modifier = Modifier.matchParentSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
    )
}

@Composable
fun CircularGlide(
    modifier: Modifier,
    url: String,
    contentScale: ContentScale,
    context: Context,
) {
    //Circular Reveal Animation
    GlideImage(
        modifier = modifier,
        imageModel = url,
        contentScale = contentScale,
        requestBuilder = {
            Glide
                .with(context)
                .asDrawable()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .sizeMultiplier(0.1f)
        },
        circularReveal = CircularReveal(duration = 350),
        loading = {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        },
        failure = {
            painterResource(id = R.drawable.ic_error_gray_24)
        }
    )
}

@Composable
fun ShimmerGlide(
    modifier: Modifier,
    url: String,
    contentScale: ContentScale,
    context: Context
) {
    //shimmerEffect 사용시 loading parameter 사용 못함
    GlideImage(
        modifier = modifier,
        imageModel = url,
        contentScale = contentScale,
        requestBuilder = {
            Glide
                .with(context)
                .asDrawable()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .sizeMultiplier(0.1f)
        },
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colorScheme.background,
            highlightColor = Color.LightGray,
            durationMillis = 350,
            dropOff = 0.65f,
            tilt = 20f
        ),
        alignment = Alignment.Center,
        failure = {
            Text(text = stringResource(R.string.image_request_failed))
        },
    )
}


@Composable
fun BasicGlide(
    url: String,
    imgHeight: Dp,
    onClick: () -> Unit
) {
    //Default Compose Glide
    GlideImage(
        modifier = Modifier
            .height(imgHeight)
            .clickable(onClick = onClick),
        model = url,
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )
}
