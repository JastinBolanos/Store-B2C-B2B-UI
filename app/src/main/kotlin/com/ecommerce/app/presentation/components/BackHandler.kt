package com.ecommerce.app.presentation.components

import androidx.compose.runtime.Composable

/**
 * ============================================================================
 * BACK HANDLER (ANDROID NATIVE JETPACK COMPOSE)
 * ============================================================================
 */
@Composable
fun BackHandler(isEnabled: Boolean = true, onBack: () -> Unit) {
    androidx.activity.compose.BackHandler(enabled = isEnabled, onBack = onBack)
}