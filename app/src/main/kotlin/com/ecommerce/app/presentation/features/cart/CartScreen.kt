package com.ecommerce.app.presentation.features.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.domain.model.CartItem
import com.ecommerce.app.presentation.components.ProductDetailDialog
import com.ecommerce.app.presentation.components.getKitImagePainter
import com.ecommerce.app.presentation.components.getProductImagePainter
import com.ecommerce.app.presentation.state.CartManager
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 🛍️ LUXURY SHOPPING BAG SCREEN (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    onProceedToCheckoutClick: () -> Unit,
    onDirectPurchaseClick: (CartItem) -> Unit
) {
    val cartItems by CartManager.cartItems.collectAsState()
    val totalCount = cartItems.sumOf { it.quantity }
    val totalAmount = cartItems.sumOf { item ->
        item.product.price * item.quantity
    }

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "SHOPPING BAG",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            if (totalCount > 0) {
                                Text(
                                    text = "$totalCount ${if (totalCount == 1) "ITEM" else "ITEMS"}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Normal,
                                    letterSpacing = 1.2.sp,
                                    color = LuxuryPalette.TextCaption
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = LuxuryPalette.Onyx
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = LuxuryPalette.PureWhite)
                )
                HorizontalDivider(color = LuxuryPalette.BorderSubtle, thickness = 1.dp)
            }
        },
        bottomBar = {
            if (cartItems.isNotEmpty()) {
                Surface(
                    color = LuxuryPalette.PureWhite,
                    shadowElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(horizontal = 20.dp, vertical = 16.dp)
                    ) {
                        // Complimentary shipping note
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "ESTIMATED TOTAL",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.5.sp,
                                color = LuxuryPalette.TextMuted
                            )
                            Text(
                                text = "$${totalAmount.format(2)}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = LuxuryPalette.Onyx
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "COMPLIMENTARY CLIMATE-NEUTRAL DELIVERY",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.2.sp,
                            color = LuxuryPalette.ForestOlive
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Luxury Matte Black Checkout Button
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(LuxuryPalette.Onyx)
                                .clickable { onProceedToCheckoutClick() },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "PROCEED TO CHECKOUT",
                                color = LuxuryPalette.PureWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.8.sp
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(LuxuryPalette.PureWhite),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = null,
                        tint = LuxuryPalette.BorderLight,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "YOUR BAG IS EMPTY",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.sp,
                        color = LuxuryPalette.Onyx
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Explore our botanical formulations, signature elixirs, and curated gift rituals.",
                        fontSize = 13.sp,
                        color = LuxuryPalette.TextMuted,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .border(1.dp, LuxuryPalette.Onyx, RoundedCornerShape(6.dp))
                            .clickable { onBackClick() }
                            .padding(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "EXPLORE THE COLLECTION",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 1.4.sp,
                            color = LuxuryPalette.Onyx
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(LuxuryPalette.PureWhite),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(cartItems, key = { it.product.id }) { item ->
                    LuxuryCartItemRow(
                        cartItem = item,
                        onDirectPurchaseClick = onDirectPurchaseClick
                    )
                    HorizontalDivider(
                        color = LuxuryPalette.BorderSubtle,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LuxuryCartItemRow(
    cartItem: CartItem,
    onDirectPurchaseClick: (CartItem) -> Unit
) {
    val product = cartItem.product
    var showDetails by remember { mutableStateOf(false) }
    val subtotal = product.price * cartItem.quantity

    val isKit = product.category.equals("Kits", ignoreCase = true)
    val imagePainter = if (isKit) {
        getKitImagePainter(product.imageUrl)
    } else {
        getProductImagePainter(product.imageUrl)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Product portrait thumbnail
        Box(
            modifier = Modifier
                .width(105.dp)
                .height(130.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(LuxuryPalette.Alabaster)
                .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp))
                .clickable { showDetails = true }
        ) {
            Image(
                painter = imagePainter,
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Product details & controls
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.category.uppercase(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.2.sp,
                        color = LuxuryPalette.ForestOlive
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = (-0.2).sp,
                        color = LuxuryPalette.Onyx,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$${product.price.format(2)} each",
                        fontSize = 12.sp,
                        color = LuxuryPalette.TextCaption
                    )
                }

                // Delete Button (minimal trash icon)
                IconButton(
                    onClick = { CartManager.removeProduct(product.id) },
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.DeleteOutline,
                        contentDescription = "Remove item",
                        tint = LuxuryPalette.TextCaption,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stepper and Item Total Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Stepper
                Row(
                    modifier = Modifier
                        .height(34.dp)
                        .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .background(LuxuryPalette.Alabaster),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            if (cartItem.quantity > 1) CartManager.addProduct(product, -1)
                        },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = LuxuryPalette.Onyx,
                            modifier = Modifier.size(12.dp)
                        )
                    }

                    Text(
                        text = cartItem.quantity.toString(),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LuxuryPalette.Onyx,
                        modifier = Modifier.padding(horizontal = 6.dp)
                    )

                    IconButton(
                        onClick = { CartManager.addProduct(product, 1) },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = LuxuryPalette.Onyx,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }

                // Subtotal
                Text(
                    text = "$${subtotal.format(2)}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LuxuryPalette.Onyx
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quick actions: View Details & Direct Checkout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(4.dp))
                        .clickable { showDetails = true }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "DETAILS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.TextMuted
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(LuxuryPalette.Onyx)
                        .clickable { onDirectPurchaseClick(cartItem) }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "BUY NOW",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.PureWhite
                    )
                }
            }
        }
    }

    if (showDetails) {
        ProductDetailDialog(
            product = product,
            imagePainter = imagePainter,
            onDismiss = { showDetails = false }
        )
    }
}

private fun Double.format(digits: Int): String {
    val rounded = (this * 100).toLong() / 100.0
    val parts = rounded.toString().split(".")
    val whole = parts[0]
    val fraction = if (parts.size > 1) parts[1] else "0"
    return "$whole.${fraction.padEnd(digits, '0')}"
}