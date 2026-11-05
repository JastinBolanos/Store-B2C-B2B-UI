package com.ecommerce.app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LuxuryColorScheme = lightColorScheme(
    primary = LuxuryPalette.Onyx,
    onPrimary = LuxuryPalette.PureWhite,
    secondary = LuxuryPalette.DeepCharcoal,
    onSecondary = LuxuryPalette.PureWhite,
    background = LuxuryPalette.PureWhite,
    onBackground = LuxuryPalette.Onyx,
    surface = LuxuryPalette.PureWhite,
    onSurface = LuxuryPalette.Onyx,
    surfaceVariant = LuxuryPalette.Alabaster,
    onSurfaceVariant = LuxuryPalette.TextMuted,
    outline = LuxuryPalette.BorderLight
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LuxuryColorScheme,
        typography = LuxuryTypography,
        content = content
    )
}
