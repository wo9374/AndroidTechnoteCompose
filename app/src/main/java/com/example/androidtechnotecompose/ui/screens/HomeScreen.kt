package com.example.androidtechnotecompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtechnotecompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    bottomNaviBarClick : () -> Unit,
    viewPagerClick : () -> Unit,
    exoPlayerClick : () -> Unit,
    bottomSheetClick : () -> Unit,
    cameraClick : () -> Unit,
    collapseClick : () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
    ) {   paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {

                //BottomNavigationBar
                Button(
                    modifier = Modifier.size(90.dp, 50.dp),
                    onClick =  bottomNaviBarClick,
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.Black
                        )
                ) {
                    Text(
                        text = stringResource(R.string.bottom_nav_bar),
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                    )
                }

                //ViewPager
                Button(
                    modifier = Modifier.size(90.dp, 50.dp),
                    onClick =  viewPagerClick,
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.Black
                        )
                ) {
                    Text(
                        text = stringResource(R.string.view_pager),
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                    )
                }

                //ExoPlayer
                Button(
                    modifier = Modifier.size(90.dp, 50.dp),
                    onClick =  exoPlayerClick,
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.Black
                        )
                ) {
                    Text(
                        text = stringResource(R.string.exo_player),
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                    )
                }

                //BottomSheet
                Button(
                    modifier = Modifier.size(90.dp, 50.dp),
                    onClick =  bottomSheetClick,
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.Black
                        )
                ) {
                    Text(
                        text = stringResource(R.string.bottom_sheet),
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                    )
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(
                    modifier = Modifier.size(90.dp, 50.dp),
                    onClick =  cameraClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = stringResource(R.string.camera),
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                    )
                }

                Button(
                    modifier = Modifier.size(90.dp, 50.dp),
                    onClick =  collapseClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = stringResource(R.string.collapse_toolbar),
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                    )
                }
            }
        }
    }
}