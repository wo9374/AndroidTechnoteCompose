package com.example.androidtechnotecompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.androidtechnotecompose.R
import com.example.androidtechnotecompose.navigation.BottomBarNavHostScreen

private const val CHILD1 = "BotNavChild1"
private const val CHILD2 = "BotNavChild2"
private const val CHILD3 = "BotNavChild3"

sealed class BottomNaviItem(
    val title: Int, val icon: ImageVector, val screenRoute: String,
) {
    object Child1 : BottomNaviItem(R.string.first, Icons.Rounded.Home, CHILD1)
    object Child2 : BottomNaviItem(R.string.second, Icons.Rounded.AddCircle, CHILD2)
    object Child3 : BottomNaviItem(R.string.third, Icons.Rounded.Settings, CHILD3)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBarScreen(navHostController: NavHostController) {
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background
            ) {
                BottomBar(navHostController)
            }
        }
    ) { innerPaddings ->
        BottomBarNavHostScreen(navHostController, innerPaddings)
    }
}

@Composable
fun BottomBar(navHostController: NavHostController){
    val items = listOf(
        BottomNaviItem.Child1,
        BottomNaviItem.Child2,
        BottomNaviItem.Child3,
    )
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navHostController.navigate(item.screenRoute){
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        //동일한 항목을 다시 선택할 때 동일한 대상의 여러 복사본 방지
                        launchSingleTop = true
                        // 이전에 선택한 항목을 다시 선택할 때 상태 복원
                        restoreState = true
                    }
                },
                label = {
                    Text(
                        text = stringResource(id = item.title),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.title} Icon"
                    )
                }
            )
        }
    }
}


@Composable
fun BotBarChildScreen1() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(R.string.first),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun BotBarChildScreen2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(R.string.second),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun BotBarChildScreen3() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = stringResource(R.string.third),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}