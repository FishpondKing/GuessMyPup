package com.airwallex.guessmypup.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PlayfulBlue,
    secondary = PlayfulGreen,
    tertiary = PlayfulGreenVariant,
    background = DarkBrown,
    surface = WarmGray,
    onPrimary = Cream,
    onSecondary = Cream,
    onTertiary = Cream,
    onBackground = Cream,
    onSurface = Cream
)

private val LightColorScheme = lightColorScheme(
    primary = PlayfulBlue,
    secondary = PlayfulGreen,
    tertiary = PlayfulGreenVariant,
    background = Cream,
    surface = Surface,
    onPrimary = Cream,
    onSecondary = Cream,
    onTertiary = Cream,
    onBackground = DarkBrown,
    onSurface = DarkBrown
)

@Composable
fun GuessMyPupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}