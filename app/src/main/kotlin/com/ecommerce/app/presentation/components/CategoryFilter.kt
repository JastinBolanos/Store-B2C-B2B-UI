package com.ecommerce.app.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 🏷️ LUXURY EDITORIAL CATEGORY TABS (MANGO MINIMALIST VIBE)
 * ============================================================================
 */
@Composable
fun CategoryFilter(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = listOf("All", "Kits", "Treatments", "Beauty", "Care", "Skin", "Others")

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(categories) { category ->
            val isSelected = category.equals(selectedCategory, ignoreCase = true)

            val animatedBgColor by animateColorAsState(
                targetValue = if (isSelected) LuxuryPalette.Onyx else LuxuryPalette.PureWhite,
                animationSpec = spring(stiffness = 400f),
                label = "cat_bg"
            )
            val animatedTextColor by animateColorAsState(
                targetValue = if (isSelected) LuxuryPalette.PureWhite else LuxuryPalette.TextMuted,
                animationSpec = spring(stiffness = 400f),
                label = "cat_text"
            )
            val animatedBorderColor by animateColorAsState(
                targetValue = if (isSelected) LuxuryPalette.Onyx else LuxuryPalette.BorderLight,
                animationSpec = spring(stiffness = 400f),
                label = "cat_border"
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onCategorySelected(category)
                    }
                    .background(animatedBgColor)
                    .border(
                        width = 1.dp,
                        color = animatedBorderColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category.uppercase(),
                    color = animatedTextColor,
                    fontSize = 11.sp,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    letterSpacing = 1.5.sp
                )
            }
        }
    }
}