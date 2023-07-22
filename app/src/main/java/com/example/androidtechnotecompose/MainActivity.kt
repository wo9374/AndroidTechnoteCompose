package com.example.androidtechnotecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.example.androidtechnotecompose.navigation.TechNoteNavHost
import com.example.androidtechnotecompose.ui.theme.TechNoteComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TechNoteApp()
        }
    }
}

@Composable
fun TechNoteApp() {
    val navController = rememberNavController()

    TechNoteComposeTheme {
        Surface {
            TechNoteNavHost(navController)
        }
    }
}