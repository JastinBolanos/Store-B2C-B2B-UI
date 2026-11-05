package com.ecommerce.app.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.domain.model.Product
import com.ecommerce.app.presentation.state.CartManager
import com.ecommerce.app.presentation.theme.LuxuryPalette
import com.ecommerce.natura.R

/**
 * ============================================================================
 * 🎁 LUXURY CURATED KIT CARD (MANGO EDITORIAL GIFT SET VIBE)
 * ============================================================================
 */
@Composable
fun LandscapeProductCard(
    product: Product,
    showCartIcon: Boolean = true,
    isAdminView: Boolean = false,
    modifier: Modifier = Modifier,
    onBuyNowClick: (Int) -> Unit = {}
) {
    val name = product.name
    val price = product.price

    val cartItems by CartManager.cartItems.collectAsState()
    val isInCart = cartItems.any { it.product.id == product.id }
    var expanded by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf(1) }
    var showDetails by remember { mutableStateOf(false) }

    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        animationSpec = spring(stiffness = 400f),
        label = "kit_arrow_rot"
    )

    val cartButtonBg by animateColorAsState(
        targetValue = if (isInCart) LuxuryPalette.Onyx else LuxuryPalette.PureWhite.copy(alpha = 0.92f),
        animationSpec = spring(stiffness = 500f),
        label = "kit_cart_btn_bg"
    )
    val cartButtonIconColor by animateColorAsState(
        targetValue = if (isInCart) LuxuryPalette.PureWhite else LuxuryPalette.Onyx,
        animationSpec = spring(stiffness = 500f),
        label = "kit_cart_btn_icon"
    )

    Column(
        modifier = modifier
            .width(260.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(LuxuryPalette.PureWhite)
            .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp))
            .animateContentSize(spring(stiffness = 600f))
    ) {
        // --- IMAGE CONTAINER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(LuxuryPalette.Alabaster)
                .clickable { showDetails = true }
        ) {
            if (product.imageUrl.isNotEmpty()) {
                Image(
                    painter = getKitImagePainter(product.imageUrl),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Limited Edition Tag
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .background(LuxuryPalette.Onyx, RoundedCornerShape(2.dp))
                    .padding(horizontal = 7.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "CURATED SET",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                    color = LuxuryPalette.PureWhite
                )
            }

            // Quick Add-to-Bag
            if (!isAdminView && showCartIcon) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(cartButtonBg)
                        .border(1.dp, LuxuryPalette.BorderLight, CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (isInCart) {
                                CartManager.removeProduct(product.id)
                            } else {
                                CartManager.addProduct(product, quantity)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isInCart) Icons.Default.Check else Icons.Outlined.ShoppingBag,
                        contentDescription = "Cart",
                        tint = cartButtonIconColor,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }

        // --- CONTENT SECTION ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = LuxuryPalette.Onyx,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 0.3.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${price.format(2)}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = LuxuryPalette.TextMuted,
                    letterSpacing = 0.5.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable { expanded = !expanded }
                        .padding(horizontal = 4.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (expanded) "CLOSE" else "ORDER",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.Onyx
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand",
                        tint = LuxuryPalette.Onyx,
                        modifier = Modifier
                            .size(16.dp)
                            .rotate(arrowRotation)
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(spring(stiffness = 500f)),
                exit = fadeOut(spring(stiffness = 500f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(4.dp))
                            .clip(RoundedCornerShape(4.dp))
                            .background(LuxuryPalette.Alabaster),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { if (quantity > 1) quantity-- },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "-", tint = LuxuryPalette.Onyx, modifier = Modifier.size(14.dp))
                        }

                        Box(
                            modifier = Modifier
                                .weight(1.2f)
                                .fillMaxHeight()
                                .background(LuxuryPalette.PureWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "$quantity", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = LuxuryPalette.Onyx)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "+", tint = LuxuryPalette.Onyx, modifier = Modifier.size(14.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (!isAdminView) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(38.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(LuxuryPalette.Onyx)
                                .clickable { onBuyNowClick(quantity) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "BUY SET • $${(price * quantity).format(2)}",
                                color = LuxuryPalette.PureWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(34.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(4.dp))
                            .background(LuxuryPalette.PureWhite)
                            .clickable { showDetails = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "VIEW SET DETAILS",
                            color = LuxuryPalette.Onyx,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    }

    if (showDetails) {
        ProductDetailDialog(
            product = product,
            imagePainter = getKitImagePainter(product.imageUrl),
            onDismiss = { showDetails = false }
        )
    }
}

// Translator for KITS (Category 5)
@Composable
fun getKitImagePainter(imageUrl: String): Painter {
    val imageRes = when (imageUrl) {
        "img_kit_citrico" -> R.drawable.img_kit_citrico
        "img_kit_botanico" -> R.drawable.img_kit_botanico
        "img_kit_minimalista" -> R.drawable.img_kit_minimalista
        "img_kit_centella" -> R.drawable.img_kit_centella
        "img_kit_higo" -> R.drawable.img_kit_higo
        "img_kit_rosas" -> R.drawable.img_kit_rosas
        else -> R.drawable.img_kit_citrico
    }
    return painterResource(imageRes)
}