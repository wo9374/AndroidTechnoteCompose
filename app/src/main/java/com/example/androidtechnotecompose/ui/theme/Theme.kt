package com.example.androidtechnotecompose.ui.theme

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    background = Color.Black,
    onBackground = Color.White,

    surface = Purple40,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    background = Color.White,
    onBackground = Color.Black,

    surface = Purple80,
    onSurface = Color.Black
)

@Composable
fun TechNoteComposeTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor: Boolean = true, // Dynamic color is available on Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isDynamicColor  && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            //val context = LocalContext.current
            //if (isDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            if (isDarkTheme) DarkColorScheme else LightColorScheme
        }

        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val bgColor = colorScheme.background.toArgb()

            val window = (view.context as Activity).window.apply {
                statusBarColor = bgColor
                navigationBarColor = bgColor
            }

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !isDarkTheme
                isAppearanceLightNavigationBars = !isDarkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}