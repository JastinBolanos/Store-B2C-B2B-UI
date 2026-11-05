package com.ecommerce.app.presentation.features.admin

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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.domain.model.Order
import com.ecommerce.app.presentation.components.ProductDetailDialog
import com.ecommerce.app.presentation.components.getKitImagePainter
import com.ecommerce.app.presentation.components.getProductImagePainter
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 📋 LUXURY ADMIN ORDERS MANAGEMENT (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    orders: List<Order>,
    selectedTab: Int,
    onTabChange: (Int) -> Unit,
    onBackClick: () -> Unit,
    onConfirmClick: (Order) -> Unit
) {
    val tabs = listOf("PENDING REVIEW", "ACTIVE DISPATCHES")

    val displayedOrders = if (selectedTab == 0) {
        orders.filter { it.status.equals("Pending", ignoreCase = true) }
    } else {
        orders.filter { !it.status.equals("Pending", ignoreCase = true) }
    }

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "ATELIER ORDERS",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Text(
                                text = "${displayedOrders.size} ORDERS IN VIEW",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Normal,
                                letterSpacing = 1.2.sp,
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

                // Editorial Tabs
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = LuxuryPalette.PureWhite,
                    contentColor = LuxuryPalette.Onyx,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            color = LuxuryPalette.Onyx,
                            height = 2.dp
                        )
                    },
                    divider = { HorizontalDivider(color = LuxuryPalette.BorderSubtle, thickness = 1.dp) }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { onTabChange(index) },
                            text = {
                                Text(
                                    text = title,
                                    fontSize = 11.sp,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                    letterSpacing = 1.4.sp,
                                    color = if (selectedTab == index) LuxuryPalette.Onyx else LuxuryPalette.TextCaption
                                )
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LuxuryPalette.PureWhite),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp)
        ) {
            items(displayedOrders, key = { it.id }) { order ->
                LuxuryAdminOrderCard(
                    order = order,
                    onConfirmClick = { onConfirmClick(order) }
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            if (displayedOrders.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(LuxuryPalette.Alabaster)
                            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Outlined.ReceiptLong,
                                contentDescription = null,
                                tint = LuxuryPalette.BorderLight,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "NO ORDERS IN THIS QUEUE",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.4.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (selectedTab == 0) "All incoming client orders have been verified." else "No active courier dispatches at this moment.",
                                fontSize = 12.sp,
                                color = LuxuryPalette.TextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LuxuryAdminOrderCard(order: Order, onConfirmClick: () -> Unit) {
    val firstProduct = order.items.firstOrNull()?.product
    val firstProductImage = firstProduct?.imageUrl ?: ""
    val isKit = firstProduct?.category.equals("Kits", ignoreCase = true)

    val imagePainter = if (isKit) {
        getKitImagePainter(firstProductImage)
    } else {
        getProductImagePainter(firstProductImage)
    }

    var showDetailsDialog by remember { mutableStateOf(false) }
    val totalItems = order.items.sumOf { it.quantity }
    val productSummary = order.items.joinToString(", ") { "${it.quantity}x ${it.product.name}" }

    val isPending = order.status.equals("Pending", ignoreCase = true)
    val isDelivered = order.status.equals("Delivered", ignoreCase = true)

    val (statusBg, statusText) = when {
        isDelivered -> LuxuryPalette.ForestOliveLight to LuxuryPalette.ForestOlive
        isPending -> LuxuryPalette.TerracottaLight to LuxuryPalette.Terracotta
        else -> LuxuryPalette.ChampagneLight to Color(0xFF8A6C35)
    }

    val actionButtonText = when {
        isPending -> "VERIFY VOUCHER"
        isDelivered -> "DISPATCH COMPLETED"
        else -> "MANAGE DISPATCH"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LuxuryPalette.PureWhite)
            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Row: Order ID and Status Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ORDER #${order.id.takeLast(6).uppercase()}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.Onyx
                    )
                    Text(
                        text = "Client: ${order.customerName}",
                        fontSize = 11.sp,
                        color = LuxuryPalette.TextCaption
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(statusBg)
                        .border(1.dp, statusText.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = order.status.uppercase(),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = statusText
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = LuxuryPalette.BorderSubtle, thickness = 1.dp)
            Spacer(modifier = Modifier.height(14.dp))

            // Body: Thumbnail and Summary
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
                        .clickable { showDetailsDialog = true }
                ) {
                    Image(
                        painter = imagePainter,
                        contentDescription = "Order Item",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = productSummary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = LuxuryPalette.Onyx,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$totalItems ${if (totalItems == 1) "item" else "items"}",
                            fontSize = 11.sp,
                            color = LuxuryPalette.TextCaption
                        )
                        Text(
                            text = "Total: $${order.totalAmount.format(2)}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = LuxuryPalette.Onyx
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "INSPECT FORMULATION",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.ForestOlive,
                        modifier = Modifier.clickable { showDetailsDialog = true }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(if (isDelivered) LuxuryPalette.Alabaster else LuxuryPalette.Onyx)
                    .border(1.dp, if (isDelivered) LuxuryPalette.BorderLight else LuxuryPalette.Onyx, RoundedCornerShape(4.dp))
                    .clickable { onConfirmClick() },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = actionButtonText,
                        color = if (isDelivered) LuxuryPalette.ForestOlive else LuxuryPalette.PureWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.4.sp
                    )
                    if (!isDelivered) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = LuxuryPalette.PureWhite,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }

    if (showDetailsDialog && firstProduct != null) {
        ProductDetailDialog(
            product = firstProduct,
            imagePainter = imagePainter,
            onDismiss = { showDetailsDialog = false }
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