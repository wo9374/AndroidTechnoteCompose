package com.example.androidtechnotecompose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
    bottomNaviBarClick: () -> Unit,
    viewPagerClick: () -> Unit,
    exoPlayerClick: () -> Unit,
    bottomSheetClick: () -> Unit,
    cameraClick: () -> Unit,
    collapseClick: () -> Unit,
    roomClick: () -> Unit,
    pagingClick: () -> Unit,
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                //BottomNavigationBar
                FunctionButton(stringResource(R.string.bottom_nav_bar), bottomNaviBarClick)

                //ViewPager
                FunctionButton(stringResource(R.string.view_pager), viewPagerClick)

                //ExoPlayer
                FunctionButton(stringResource(R.string.exo_player), exoPlayerClick)

                //BottomSheet
                FunctionButton(stringResource(R.string.bottom_sheet), bottomSheetClick)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //Camera
                FunctionButton(stringResource(R.string.camera), cameraClick)

                //Collapse Toolbar
                FunctionButton(stringResource(R.string.collapse_toolbar), collapseClick)

                //Room
                FunctionButton(stringResource(R.string.room), roomClick)

                //Paging
                FunctionButton(stringResource(R.string.paging), pagingClick)
            }
        }
    }
}

@Composable
fun FunctionButton(
    btnTxt: String,
    btnClick: () -> Unit,
) {
    Button(
        modifier = Modifier.size(90.dp, 50.dp),
        onClick = btnClick,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.Black
            )
    ) {
        Text(
            text = btnTxt,
            textAlign = TextAlign.Center,
            fontSize = 10.sp,
        )
    }
}