package com.ecommerce.app.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.state.CartManager
import com.ecommerce.app.presentation.theme.LuxuryPalette
import com.ecommerce.natura.R

/**
 * ============================================================================
 * 🔝 LUXURY EDITORIAL TOP NAVIGATION BAR (MANGO MINIMALIST VIBE)
 * ============================================================================
 */
@Composable
fun TopNavBar(
    modifier: Modifier = Modifier,
    onCartClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val cartItems by CartManager.cartItems.collectAsState()
    val totalItemsCount = cartItems.sumOf { it.quantity }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- BRAND LOGO / SIGNATURE ---
        Column(verticalArrangement = Arrangement.Center) {
            Text(
                text = "Remedioz Natura",
                fontFamily = FontFamily(Font(R.font.imperial_script)),
                fontWeight = FontWeight.Normal,
                fontSize = 36.sp,
                color = LuxuryPalette.Onyx
            )
            Text(
                text = "ORGANIC BOTANICALS",
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.5.sp,
                color = LuxuryPalette.TextCaption
            )
        }

        // --- RIGHT ACTION ICONS ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Profile Icon
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Account",
                    tint = LuxuryPalette.Onyx,
                    modifier = Modifier.size(23.dp)
                )
            }

            // Shopping Bag with animated count pill
            Box(contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = onCartClick,
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = "Shopping Bag",
                        tint = LuxuryPalette.Onyx,
                        modifier = Modifier.size(23.dp)
                    )
                }

                // Animated Badge
                if (totalItemsCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 4.dp, end = 4.dp)
                            .size(17.dp)
                            .clip(CircleShape)
                            .background(LuxuryPalette.Onyx),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (totalItemsCount > 99) "99+" else "$totalItemsCount",
                            color = LuxuryPalette.PureWhite,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}