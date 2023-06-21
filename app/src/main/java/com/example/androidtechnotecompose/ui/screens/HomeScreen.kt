package com.example.androidtechnotecompose.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    bottomNaviBarClick : () -> Unit,
    viewPagerClick : () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "홈") },
            )
        },
    ) {   paddingValues ->

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

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
                    text = "Bottom\nNaviBar",
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
                    text = "ViewPager",
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                )
            }
        }
    }
}