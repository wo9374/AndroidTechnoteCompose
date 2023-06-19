package com.example.androidtechnotecompose.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidtechnotecompose.ui.screens.BotBarChildScreen1
import com.example.androidtechnotecompose.ui.screens.BotBarChildScreen2
import com.example.androidtechnotecompose.ui.screens.BotBarChildScreen3
import com.example.androidtechnotecompose.ui.screens.BottomNaviItem


@Composable
fun BottomBarNavHostScreen(navHostController: NavHostController, innerPadding : PaddingValues){
    NavHost(
        navController = navHostController,
        startDestination = BottomNaviItem.Child1.screenRoute,
        Modifier.padding(innerPadding)
    ){
        composable(BottomNaviItem.Child1.screenRoute){ BotBarChildScreen1() }
        composable(BottomNaviItem.Child2.screenRoute){ BotBarChildScreen2() }
        composable(BottomNaviItem.Child3.screenRoute){ BotBarChildScreen3() }
    }
}