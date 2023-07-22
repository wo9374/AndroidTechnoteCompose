@file:OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)

package com.example.androidtechnotecompose.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.imageResource
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
import com.bumptech.glide.RequestManager
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.GlideLazyListPreloader
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.example.androidtechnotecompose.R
import com.example.androidtechnotecompose.viewmodel.PagingViewModel
import com.example.domain.entity.UnsplashEntity
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun PagingScreen(
    viewModel: PagingViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val state = rememberLazyListState()
    val pagingItems: LazyPagingItems<UnsplashEntity> =
        viewModel.unsplashList.collectAsLazyPagingItems()
    val itemHeight = 100.dp

    val textState = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val emptySearchTxt = stringResource(id = R.string.search_photo)

            TopAppBar(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                title = {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(end = 18.dp),
                        value = textState.value,
                        onValueChange = { textValue ->
                            textState.value = textValue
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
                            if (textState.value.isNotEmpty()) {
                                coroutineScope.launch {

                                }
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
        },
    ) { innerPadding ->
        //loading 분기 https://betterprogramming.pub/turn-the-page-overview-of-android-paging3-library-integration-with-jetpack-compose-3a7881ed75b4
        when (pagingItems.loadState.refresh) {
            is LoadState.Error -> {

            }

            is LoadState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = "Pagination Loading")
                    CircularProgressIndicator(color = Color.Black)
                }
            }

            else -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
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
    //Default Compose Glide
    /*GlideImage(
        modifier = Modifier.height(squareHeight).clickable(onClick = onClick),
        model = entity.urlStr,
        contentDescription = "",
        contentScale = ContentScale.Crop,
    )*/

    /*GlideImage(
        imageModel = entity.urlStr,
        modifier = Modifier.height(squareHeight),
        requestBuilder = {
            Glide
                .with(LocalView.current)
                .asDrawable()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .sizeMultiplier(0.1f)
        },
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        loading = {

        },
        success = {

        },
    )*/

    //shimmerEffect 사용시 loading parameter 사용 못함
    GlideImage( // CoilImage, FrescoImage
        imageModel = entity.urlStr,
        modifier = Modifier.height(squareHeight),
        requestBuilder = {
            Glide
                .with(LocalView.current)
                .asDrawable()
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .sizeMultiplier(0.1f)
        },
        shimmerParams = ShimmerParams(
            baseColor = MaterialTheme.colorScheme.background,
            highlightColor = MaterialTheme.colorScheme.onPrimary,
            durationMillis = 350,
            dropOff = 0.65f,
            tilt = 20f
        ),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        // shows an error text message when request failed.
        failure = {
            Text(text = "image request failed.")
        },
    )

    //Circular Reveal Animation
    /*GlideImage(
        imageModel = entity.urlStr,
        contentScale = ContentScale.Crop,
        circularReveal = CircularReveal(duration = 350),
        placeHolder = ImageBitmap.imageResource(R.drawable.ic_fullscreen_24),
        error = ImageBitmap.imageResource(R.drawable.ic_error_gray_24)
    )*/
}