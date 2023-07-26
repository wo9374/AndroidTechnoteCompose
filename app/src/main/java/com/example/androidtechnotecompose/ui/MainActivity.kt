package com.example.androidtechnotecompose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
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