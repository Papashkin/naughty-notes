package com.antsfamily.naughtynotes.ui.theme

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.graphics.drawable.toDrawable

@Suppress("DEPRECATION")
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val colorScheme = if (darkTheme) darkScheme else lightScheme

    val useDarkIcons = !darkTheme

    SideEffect {
        window.statusBarColor = colorScheme.surface.toArgb()
        window.navigationBarColor = colorScheme.surface.toArgb()
        window.setBackgroundDrawable(colorScheme.surface.toArgb().toDrawable())

        val systemUiController = WindowCompat.getInsetsController(window, view)
        systemUiController.isAppearanceLightStatusBars = useDarkIcons
        systemUiController.isAppearanceLightNavigationBars = useDarkIcons

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.navigationBarDividerColor = Color.Transparent.toArgb()
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = appTypography,
        content = content
    )
}
