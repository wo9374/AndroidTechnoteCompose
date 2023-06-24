@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.example.androidtechnotecompose.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androidtechnotecompose.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


/**
 * View Deeps 로 인해 ModalBottomSheetLayout 안에 BottomSheetScaffold 를 구현해
 * ModalSheet 와 PersistentSheet 가 같이 보이게 구현
 * */

@Composable
fun BottomSheetScreen() {
    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { false },
        /*
        * BottomSheet 의 동작을 제어 하려면 confirmStateChange 가 필요
        * false 시 사용자가 맨 아래 시트를 끌거나 밖을 눌러 해제할 수 없음
        *
        * confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
        * 일반적으로 HalfExpanded 상태가 아닐때를 사용 대부분 true (외부 Touch 허용)
        * */

        skipHalfExpanded = false, //시트의 높이가 충분할 경우 HalfExpanded 상태를 건너뛸지 여부


//        animationSpec = tween(
//            durationMillis = 300, //default 300
//            delayMillis = 0,
//            easing = FastOutLinearInEasing
//        )
        //tween API를 이용해 Animation 시간과 효과를 정해 사용
    )

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    var isSheetFullScreen by remember { mutableStateOf(false) }
    val roundedCornerRadius = if (isSheetFullScreen) 0.dp else 12.dp
    val modalScreenSize = if (isSheetFullScreen)
        Modifier.fillMaxSize()
    else
        Modifier.fillMaxWidth()

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(
            topStart = roundedCornerRadius,
            topEnd = roundedCornerRadius
        ), //Sheet 모양
        sheetContent = {
            Column(
                modifier = modalScreenSize.background(Color.Black.copy(0.3f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = { isSheetFullScreen = !isSheetFullScreen }
                ) {
                    Text(text = stringResource(R.string.toggle_full_screen))
                }

                Button(
                    onClick = { coroutineScope.launch { modalSheetState.hide() } }
                ) {
                    Text(text = stringResource(R.string.hide_sheet))
                }
            }
        },
        //scrimColor = Color.Yellow,    //Shadow Color
        //Color.Unspecified 적용시 그림자 제거, 나머지 뒤 화면의 상호 작용 차단 하지 않음
    ) {
        PersistentSheet(
            coroutineScope,
            modalStateClick = {
                coroutineScope.launch {
                    if (modalSheetState.isVisible)
                        modalSheetState.hide()
                    else
                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                }
            },
            cornerRadius = roundedCornerRadius
        )
    }
}

@Composable
fun PersistentSheet(
    coroutineScope: CoroutineScope,
    modalStateClick: () -> Unit,
    cornerRadius: Dp,
) {
    val persistentSheetState = rememberBottomSheetScaffoldState()

    val peekHeight = 50.dp
    val sheetPadding = 18.dp
    val dividerHeight = 3.dp

    BottomSheetScaffold(
        scaffoldState = persistentSheetState,
        sheetPeekHeight = peekHeight,
        sheetShape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius), //Sheet 모양
        sheetContent = {
            //첫 번째 보이는 항목 (PersistentSheet 의 화면 일부 보이는 부분)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = peekHeight)
                    .background(Color.Black.copy(0.3f))
                    .padding(sheetPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier.size(70.dp, dividerHeight).background(Color.White)
                )
                Spacer(
                    modifier = Modifier
                        .height(
                            peekHeight - (sheetPadding + dividerHeight)
                        )
                )

                LazyColumn {
                    items(count = 5) {
                        ListItem(
                            modifier = Modifier.clickable {
                                coroutineScope.launch {
                                    persistentSheetState.bottomSheetState.collapse()
                                }
                            },
                            text = { Text(text = "Item $it") },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorite",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                }
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
        {
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = modalStateClick,
            ) {
                Text(text = stringResource(R.string.modal_sheet))
            }
        }
    }
}