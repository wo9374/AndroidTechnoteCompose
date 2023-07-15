@file:OptIn(ExperimentalPagerApi::class, ExperimentalPagerApi::class)

package com.example.androidtechnotecompose.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtechnotecompose.extensions.shuffleColors
import com.example.androidtechnotecompose.ui.theme.HoloBlueLight
import com.example.androidtechnotecompose.ui.theme.HoloGreenDark
import com.example.androidtechnotecompose.ui.theme.HoloOrangeDark
import com.example.androidtechnotecompose.ui.theme.HoloPurple
import com.example.androidtechnotecompose.ui.theme.HoloRedLight
import com.example.androidtechnotecompose.ui.theme.TransferBlack
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private data class ColorData(val colors: List<Color>)

private val colorData = ColorData(
    listOf(HoloRedLight, HoloOrangeDark, HoloGreenDark, HoloBlueLight, HoloPurple)
)

@Composable
fun ViewPagerScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        val rowColors = colorData.colors
        val columnColors = colorData.copy().colors.shuffleColors()

        ColumnPager(colorList = columnColors)
        RowPager(colorList = rowColors)
    }
}

@Composable
fun RowPager(colorList: List<Color>) {
    val rowState = rememberPagerState()

    //드래그 판단 AutoScroll Stop
    val isDragged by rowState.interactionSource.collectIsDraggedAsState()
    if (!isDragged) {
        LaunchedEffect(key1 = rowState.currentPage) {
            launch {
                delay(3000)
                with(rowState) {
                    val target = if (currentPage < pageCount - 1) currentPage + 1 else 0

                    tween<Float>(
                        durationMillis = 2000,
                        easing = FastOutSlowInEasing
                    )
                    animateScrollToPage(page = target)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
    ) {
        HorizontalPager(
            count = colorList.size,
            state = rowState
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorList[page]),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    color = Color.White,
                    text = "${page + 1} Page",
                )
            }

        }

        Text(modifier = Modifier.align(Alignment.TopCenter), text = "Horizontal")

        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(70.dp, 26.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(TransferBlack)
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = Color.White,
                text = "${rowState.currentPage + 1} / ${colorList.size}",
            )
        }
    }
}

@Composable
fun ColumnPager(colorList: List<Color>) {
    val columnState = rememberPagerState()

    val isDragged by columnState.interactionSource.collectIsDraggedAsState()
    if (!isDragged) {
        LaunchedEffect(key1 = columnState.currentPage) {
            launch {
                delay(3000)
                with(columnState) {
                    val target = if (currentPage < pageCount - 1) currentPage + 1 else 0

                    tween<Float>(
                        durationMillis = 2000,
                        easing = LinearOutSlowInEasing
                    )
                    animateScrollToPage(page = target)
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
    ) {
        VerticalPager(
            count = colorList.size,
            state = columnState
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorList[page]),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    color = Color.White,
                    text = "${page + 1} Page",
                )
            }
        }

        Text(modifier = Modifier.align(Alignment.TopCenter), text = "Vertical")

        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(70.dp, 26.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(TransferBlack)
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            Text(
                color = Color.White,
                text = "${columnState.currentPage + 1} / ${colorList.size}",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RowPagerPreview() {
    RowPager(colorList = colorData.colors)
}

@Preview(showBackground = true)
@Composable
fun ColumnPagerPreview() {
    ColumnPager(colorList = colorData.copy().colors.shuffled())
}