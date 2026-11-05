package com.ecommerce.app.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 🔎 LUXURY EDITORIAL SEARCH INPUT (SMOOTH GPU ANIMATION)
 * ============================================================================
 */
@Composable
fun SearchInput(
    query: String,
    onQueryChange: (String) -> Unit,
    isFocused: Boolean,
    onFocusChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val infiniteTransition = rememberInfiniteTransition(label = "luxury_search_glow")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "search_angle"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .drawBehind {
                if (isFocused) {
                    rotate(angle) {
                        drawCircle(
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    LuxuryPalette.Champagne.copy(alpha = 0.5f),
                                    LuxuryPalette.Onyx,
                                    LuxuryPalette.Champagne,
                                    Color.Transparent
                                )
                            ),
                            radius = size.width
                        )
                    }
                } else {
                    drawRect(LuxuryPalette.BorderLight)
                }
            }
            .padding(1.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(LuxuryPalette.Alabaster)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = "SEARCH THE COLLECTION...",
                    fontSize = 11.sp,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Normal,
                    color = LuxuryPalette.TextCaption
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = if (isFocused) LuxuryPalette.Onyx else LuxuryPalette.TextCaption,
                    modifier = Modifier.size(19.dp)
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = query.isNotEmpty(),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(
                        onClick = {
                            onQueryChange("")
                            focusManager.clearFocus()
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Clear search",
                            tint = LuxuryPalette.TextCaption,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            },
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 13.sp,
                color = LuxuryPalette.Onyx,
                letterSpacing = 0.5.sp
            ),
            shape = RoundedCornerShape(7.dp),
            modifier = Modifier
                .fillMaxSize()
                .onFocusChanged { onFocusChange(it.isFocused) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                cursorColor = LuxuryPalette.Onyx
            )
        )
    }
}