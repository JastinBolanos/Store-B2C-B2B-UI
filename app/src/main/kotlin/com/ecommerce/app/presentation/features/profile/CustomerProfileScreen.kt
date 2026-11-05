package com.ecommerce.app.presentation.features.profile

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.domain.model.Order
import com.ecommerce.app.presentation.components.getKitImagePainter
import com.ecommerce.app.presentation.components.getProductImagePainter
import com.ecommerce.app.presentation.theme.LuxuryPalette
import com.ecommerce.natura.R

/**
 * ============================================================================
 * 👤 LUXURY CLIENT PROFILE & ATELIER ORDERS (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerProfileScreen(
    userName: String?,
    userEmail: String?,
    userPhotoUrl: String?,
    orders: List<Order>,
    onBackClick: () -> Unit,
    onTrackOrderClick: (Order) -> Unit
) {
    // Subtle ambient rotation for avatar halo
    val infiniteTransition = rememberInfiniteTransition(label = "profile_halo_animation")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "halo_rotation"
    )

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "CLIENT PROFILE",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Text(
                                text = "REMEDIOZ NATURA ATELIER",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.4.sp,
                                color = LuxuryPalette.TextCaption
                            )
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
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LuxuryPalette.PureWhite),
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {
            // --- 1. LUXURY PROFILE HEADER CARD ---
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(LuxuryPalette.Alabaster)
                            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                            .padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Avatar with luxury rotating ambient halo
                            Box(
                                modifier = Modifier
                                    .size(86.dp)
                                    .clip(CircleShape)
                                    .drawBehind {
                                        rotate(angle) {
                                            drawCircle(
                                                brush = Brush.sweepGradient(
                                                    colors = listOf(
                                                        Color.Transparent,
                                                        LuxuryPalette.Champagne.copy(alpha = 0.7f),
                                                        LuxuryPalette.BorderLight,
                                                        LuxuryPalette.Onyx.copy(alpha = 0.4f),
                                                        Color.Transparent
                                                    )
                                                ),
                                                radius = size.width
                                            )
                                        }
                                    }
                                    .padding(2.5.dp)
                                    .clip(CircleShape)
                                    .background(LuxuryPalette.PureWhite),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.img_perfil),
                                    contentDescription = "Profile picture",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(18.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                // Membership Badge
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(LuxuryPalette.ForestOliveLight)
                                        .border(1.dp, LuxuryPalette.ForestOlive.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = "ATELIER PRIVILEGE CLIENT",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.2.sp,
                                        color = LuxuryPalette.ForestOlive
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = userName ?: "Laura Veracruz",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = (-0.2).sp,
                                    color = LuxuryPalette.Onyx
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = userEmail ?: "laura.official@gmail.com",
                                    fontSize = 12.sp,
                                    color = LuxuryPalette.TextMuted,
                                    letterSpacing = 0.3.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Atelier Status Strip (Pillars)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProfileStatPill(
                            label = "TOTAL RITUALS",
                            value = "${orders.size}",
                            modifier = Modifier.weight(1f)
                        )
                        ProfileStatPill(
                            label = "SUSTAINABILITY",
                            value = "Organic",
                            modifier = Modifier.weight(1f)
                        )
                        ProfileStatPill(
                            label = "CONCIERGE",
                            value = "Active",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Section Title: Order History
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Text(
                                text = "ACQUIRED FORMULATIONS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.6.sp,
                                color = LuxuryPalette.ForestOlive
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "ORDER HISTORY & PARCELS",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.2.sp,
                                color = LuxuryPalette.Onyx
                            )
                        }

                        if (orders.isNotEmpty()) {
                            Text(
                                text = "${orders.size} ${if (orders.size == 1) "ORDER" else "ORDERS"}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 1.sp,
                                color = LuxuryPalette.TextCaption
                            )
                        }
                    }
                }
            }

            // --- 2. LUXURY ORDER CARDS ---
            if (orders.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 24.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(LuxuryPalette.Alabaster)
                            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingBag,
                                contentDescription = null,
                                tint = LuxuryPalette.BorderLight,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "NO ORDERS RECORDED YET",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.8.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Your bespoke botanical extractions and gift sets will appear here once acquired.",
                                fontSize = 12.sp,
                                color = LuxuryPalette.TextMuted,
                                textAlign = TextAlign.Center,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(LuxuryPalette.Onyx)
                                    .clickable { onBackClick() }
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = "EXPLORE THE COLLECTION",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    letterSpacing = 1.4.sp,
                                    color = LuxuryPalette.PureWhite
                                )
                            }
                        }
                    }
                }
            } else {
                items(orders, key = { it.id }) { order ->
                    LuxuryCustomerOrderCard(
                        order = order,
                        onTrackClick = { onTrackOrderClick(order) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            // --- 3. CONCIERGE HELP FOOTER STRIP ---
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(LuxuryPalette.Alabaster)
                        .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp))
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Spa,
                            contentDescription = null,
                            tint = LuxuryPalette.ForestOlive,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "BOTANICAL CONCIERGE ASSISTANCE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Need tailored guidance with your ritual order? Our atelier team is available.",
                                fontSize = 11.sp,
                                color = LuxuryPalette.TextMuted,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * High-fashion Order Card for the Client Profile
 */
@Composable
private fun LuxuryCustomerOrderCard(
    order: Order,
    onTrackClick: () -> Unit
) {
    val firstProduct = order.items.firstOrNull()?.product
    val firstProductImage = firstProduct?.imageUrl ?: ""
    val firstProductName = firstProduct?.name ?: "Remedioz Botanical Formulation"
    val totalItems = order.items.sumOf { it.quantity }

    val isKit = firstProduct?.category.equals("Kits", ignoreCase = true)
    val imagePainter = if (isKit) {
        getKitImagePainter(firstProductImage)
    } else {
        getProductImagePainter(firstProductImage)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(LuxuryPalette.PureWhite)
            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header: Order ID + Status Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "ORDER #${order.id.takeLast(6).uppercase()}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.Onyx
                    )
                }

                OrderStatusPill(status = order.status)
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = LuxuryPalette.BorderSubtle, thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            // Body: Thumbnail + Product Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(72.dp)
                        .height(88.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(LuxuryPalette.Alabaster)
                        .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(4.dp))
                ) {
                    Image(
                        painter = imagePainter,
                        contentDescription = firstProductName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isKit) "CURATED GIFT SET" else "BOTANICAL EXTRACT",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.2.sp,
                        color = LuxuryPalette.ForestOlive
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = firstProductName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = (-0.2).sp,
                        color = LuxuryPalette.Onyx,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$totalItems ${if (totalItems == 1) "item" else "items"} in this dispatch",
                        fontSize = 11.sp,
                        color = LuxuryPalette.TextCaption
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Total: $${order.totalAmount.format(2)}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LuxuryPalette.Onyx
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Track Order Action
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(4.dp))
                    .clickable { onTrackClick() }
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocalShipping,
                        contentDescription = null,
                        tint = LuxuryPalette.Onyx,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "TRACK DISPATCH PROGRESS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.2.sp,
                        color = LuxuryPalette.Onyx
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = LuxuryPalette.Onyx,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

/**
 * Editorial Order Status Pill
 */
@Composable
private fun OrderStatusPill(status: String) {
    val (bgColor, textColor, borderCol) = when (status.lowercase()) {
        "delivered" -> Triple(LuxuryPalette.ForestOliveLight, LuxuryPalette.ForestOlive, LuxuryPalette.ForestOlive.copy(alpha = 0.3f))
        "approved" -> Triple(LuxuryPalette.ChampagneLight, Color(0xFF8A6C35), LuxuryPalette.Champagne.copy(alpha = 0.5f))
        "rejected" -> Triple(LuxuryPalette.TerracottaLight, LuxuryPalette.Terracotta, LuxuryPalette.Terracotta.copy(alpha = 0.3f))
        "in transit", "preparing package" -> Triple(LuxuryPalette.Onyx, LuxuryPalette.PureWhite, LuxuryPalette.Onyx)
        else -> Triple(LuxuryPalette.Alabaster, LuxuryPalette.TextMuted, LuxuryPalette.BorderLight)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bgColor)
            .border(1.dp, borderCol, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = status.uppercase(),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = textColor
        )
    }
}

/**
 * Client Profile Stat Pill
 */
@Composable
private fun ProfileStatPill(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(LuxuryPalette.Alabaster)
            .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp))
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = LuxuryPalette.Onyx
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 8.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.sp,
                color = LuxuryPalette.TextCaption
            )
        }
    }
}

private fun Double.format(digits: Int): String {
    val rounded = (this * 100).toLong() / 100.0
    val parts = rounded.toString().split(".")
    val whole = parts[0]
    val fraction = if (parts.size > 1) parts[1] else "0"
    return "$whole.${fraction.padEnd(digits, '0')}"
}