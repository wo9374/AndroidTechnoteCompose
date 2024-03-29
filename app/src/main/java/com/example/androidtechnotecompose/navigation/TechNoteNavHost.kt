package com.example.androidtechnotecompose.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androidtechnotecompose.ui.screens.BottomNavBarScreen
import com.example.androidtechnotecompose.ui.screens.BottomSheetScreen
import com.example.androidtechnotecompose.ui.screens.CalendarScreen
import com.example.androidtechnotecompose.ui.screens.CameraScreen
import com.example.androidtechnotecompose.ui.screens.CollapsingToolbarScreen
import com.example.androidtechnotecompose.ui.screens.ExoPlayerScreen
import com.example.androidtechnotecompose.ui.screens.HomeScreen
import com.example.androidtechnotecompose.ui.screens.PagingScreen
import com.example.androidtechnotecompose.ui.screens.RoomScreen
import com.example.androidtechnotecompose.ui.screens.ViewPagerScreen

enum class TechNoteScreen {
    Home,
    BottomNavigation, ViewPager, ExoPlayer, BottomSheet,
    Camera, CollapseToolbar, Room, Paging,
    Calendar,
}

@Composable
fun TechNoteNavHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        modifier = Modifier.padding(paddingValues),
        navController = navController,
        startDestination = TechNoteScreen.Home.name
    ) {

        composable(TechNoteScreen.Home.name) {
            navController.apply {
                HomeScreen(
                    bottomNaviBarClick = { navigate(TechNoteScreen.BottomNavigation.name) },
                    viewPagerClick = { navigate(TechNoteScreen.ViewPager.name) },
                    exoPlayerClick = { navigate(TechNoteScreen.ExoPlayer.name) },
                    bottomSheetClick = { navigate(TechNoteScreen.BottomSheet.name) },
                    cameraClick = { navigate(TechNoteScreen.Camera.name) },
                    collapseClick = { navigate(TechNoteScreen.CollapseToolbar.name) },
                    roomClick = { navigate(TechNoteScreen.Room.name) },
                    pagingClick = { navigate(TechNoteScreen.Paging.name)},
                    calendarClick = { navigate(TechNoteScreen.Calendar.name)},
                )
            }
        }

        composable(TechNoteScreen.BottomNavigation.name) {
            val botNavHostController = rememberNavController()
            BottomNavBarScreen(botNavHostController)
        }
        composable(TechNoteScreen.ViewPager.name) { ViewPagerScreen() }
        composable(TechNoteScreen.ExoPlayer.name) { ExoPlayerScreen() }
        composable(TechNoteScreen.BottomSheet.name) { BottomSheetScreen() }
        composable(TechNoteScreen.Camera.name) { CameraScreen() }
        composable(TechNoteScreen.CollapseToolbar.name) { CollapsingToolbarScreen() }
        composable(TechNoteScreen.Room.name) { RoomScreen() }
        composable(TechNoteScreen.Paging.name) { PagingScreen() }
        composable(TechNoteScreen.Calendar.name) { CalendarScreen() }
    }
}


//NagGraph 분할, 그래프의 크기가 커질수록 그래프를 여러 메서드로 분할하는것을 추천하며 분할이 가능
/*
fun NavGraphBuilder.loginGraph(navController: NavController) {
    navigation(startDestination = "username", route = "login") {
        composable("username") { ... }
        composable("password") { ... }
        composable("registration") { ... }
    }
}*/

/*분할된 그래프들을 정리하려면 다음과 같이
NavHost(navController, startDestination = "home") {
    communityGraph(navController)
    loginGraph(navController)
    setttingGraph(navController)
}*/
