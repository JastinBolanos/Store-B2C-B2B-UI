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
 * 🛍️ LUXURY EDITORIAL PRODUCT CARD (MANGO RETAIL VIBE)
 * ============================================================================
 */
@Composable
fun ProductCard(
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
        label = "arrow_rot"
    )

    val cartButtonBg by animateColorAsState(
        targetValue = if (isInCart) LuxuryPalette.Onyx else LuxuryPalette.PureWhite.copy(alpha = 0.92f),
        animationSpec = spring(stiffness = 500f),
        label = "cart_btn_bg"
    )
    val cartButtonIconColor by animateColorAsState(
        targetValue = if (isInCart) LuxuryPalette.PureWhite else LuxuryPalette.Onyx,
        animationSpec = spring(stiffness = 500f),
        label = "cart_btn_icon"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(LuxuryPalette.PureWhite)
            .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp))
            .animateContentSize(spring(stiffness = 600f))
    ) {
        // --- 1. EDITORIAL PRODUCT IMAGE CONTAINER ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.82f)
                .background(LuxuryPalette.Alabaster)
                .clickable { showDetails = true }
        ) {
            if (product.imageUrl.isNotEmpty()) {
                Image(
                    painter = getProductImagePainter(product.imageUrl),
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Subtle Category Pill (Top-Left)
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart)
                    .background(LuxuryPalette.PureWhite.copy(alpha = 0.88f), RoundedCornerShape(2.dp))
                    .padding(horizontal = 6.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "ORGANIC",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.2.sp,
                    color = LuxuryPalette.Onyx
                )
            }

            // Quick Add-to-Bag Floating Circle Button (Top-Right)
            if (showCartIcon) {
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
                                CartManager.removeProduct(productId = product.id)
                            } else {
                                CartManager.addProduct(product = product, quantity = quantity)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isInCart) Icons.Default.Check else Icons.Outlined.ShoppingBag,
                        contentDescription = if (isInCart) "In cart" else "Add to cart",
                        tint = cartButtonIconColor,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }
        }

        // --- 2. EDITORIAL PRODUCT INFORMATION ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            // Product Name
            Text(
                text = name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = LuxuryPalette.Onyx,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                letterSpacing = 0.3.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Price & Quick Action Row
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

                // Toggle Quick-Buy Accordion
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

            // --- 3. EXPANDABLE LUXURY ORDER ACCORDION ---
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
                    // Minimalist Stepper [ −  1  + ]
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
                            Icon(
                                imageVector = Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint = LuxuryPalette.Onyx,
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1.2f)
                                .fillMaxHeight()
                                .background(LuxuryPalette.PureWhite),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$quantity",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = LuxuryPalette.Onyx
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clickable { quantity++ },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Increase",
                                tint = LuxuryPalette.Onyx,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (!isAdminView) {
                        // High-Fashion Solid Black Button
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
                                text = "BUY NOW • $${(price * quantity).format(2)}",
                                color = LuxuryPalette.PureWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    // View Details link button
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
                            text = "VIEW FULL DETAILS",
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
            imagePainter = getProductImagePainter(product.imageUrl),
            onDismiss = { showDetails = false }
        )
    }
}

fun Double.format(digits: Int): String {
    val rounded = (this * 100).toLong() / 100.0
    val parts = rounded.toString().split(".")
    val whole = parts[0]
    val fraction = if (parts.size > 1) parts[1] else "0"
    return "$whole.${fraction.padEnd(digits, '0')}"
}

@Composable
fun getProductImagePainter(imageUrl: String): Painter {
    val imageRes = when (imageUrl) {
        // --- CATEGORY 1: SKIN ---
        "img_serum_pink_peptide" -> R.drawable.img_serum_pink_peptide
        "img_crema_dolkong" -> R.drawable.img_crema_dolkong
        "img_tonico_purificante" -> R.drawable.img_tonico_purificante
        "img_crema_noche" -> R.drawable.img_crema_noche
        "img_jabon_manzanilla" -> R.drawable.img_jabon_manzanilla
        "img_jabon_arcilla" -> R.drawable.img_jabon_arcilla

        // --- CATEGORY 2: BEAUTY ---
        "img_balsamo_citrico" -> R.drawable.img_balsamo_citrico
        "img_perfume_rojo" -> R.drawable.img_perfume_rojo
        "img_tinte_seda" -> R.drawable.img_tinte_seda
        "img_perfume_azul" -> R.drawable.img_perfume_azul
        "img_rubor_liquido" -> R.drawable.img_rubor_liquido
        "img_balsamo_herbal" -> R.drawable.img_balsamo_herbal

        // --- CATEGORY 3: CARE ---
        "img_aceite_dorado" -> R.drawable.img_aceite_dorado
        "img_mantequilla_caramelo" -> R.drawable.img_mantequilla_caramelo
        "img_aceite_macadamia" -> R.drawable.img_aceite_macadamia
        "img_shampoo_solido" -> R.drawable.img_shampoo_solido
        "img_exfoliante_corporal" -> R.drawable.img_exfoliante_corporal
        "img_jabon_avena" -> R.drawable.img_jabon_avena

        // --- CATEGORY 4: TREATMENTS ---
        "img_esencia_lechosa" -> R.drawable.img_esencia_lechosa
        "img_mascarilla_citrica" -> R.drawable.img_mascarilla_citrica
        "img_tratamiento_capilar" -> R.drawable.img_tratamiento_capilar
        "img_gel_vitamina_c" -> R.drawable.img_gel_vitamina_c
        "img_tratamiento_cuero_cabelludo" -> R.drawable.img_tratamiento_cuero_cabelludo
        "img_mascarilla_miel" -> R.drawable.img_mascarilla_miel

        // --- FALLBACK ---
        else -> R.drawable.img_tinte_seda
    }
    return painterResource(imageRes)
}