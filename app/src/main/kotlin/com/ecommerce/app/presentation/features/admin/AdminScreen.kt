package com.ecommerce.app.presentation.features.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 🛠️ LUXURY ATELIER ADMIN HUB (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    pendingOrdersCount: Int,
    onBackClick: () -> Unit,
    onNavigateToOrders: () -> Unit,
    onNavigateToEditProducts: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToUpdatePayment: () -> Unit
) {
    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "ATELIER MANAGEMENT",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Text(
                                text = "REMEDIOZ NATURA PORTAL",
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
                                contentDescription = "Return",
                                tint = LuxuryPalette.Onyx
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onNavigateToNotifications) {
                            BadgedBox(
                                badge = {
                                    if (pendingOrdersCount > 0) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(CircleShape)
                                                .background(LuxuryPalette.Onyx),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = if (pendingOrdersCount > 99) "99+" else "$pendingOrdersCount",
                                                color = LuxuryPalette.PureWhite,
                                                fontSize = 9.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Notifications,
                                    contentDescription = "Notifications",
                                    tint = LuxuryPalette.Onyx,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = LuxuryPalette.PureWhite)
                )
                HorizontalDivider(color = LuxuryPalette.BorderSubtle, thickness = 1.dp)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LuxuryPalette.PureWhite)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // --- 1. ATELIER STATUS BANNER ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                    .padding(20.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ATELIER OPERATIONS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp,
                            color = LuxuryPalette.ForestOlive
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (pendingOrdersCount > 0) LuxuryPalette.TerracottaLight else LuxuryPalette.ForestOliveLight)
                                .border(
                                    1.dp,
                                    if (pendingOrdersCount > 0) LuxuryPalette.Terracotta.copy(alpha = 0.3f) else LuxuryPalette.ForestOlive.copy(alpha = 0.3f),
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = if (pendingOrdersCount > 0) "$pendingOrdersCount PENDING VERIFICATION" else "ALL ORDERS DISPATCHED",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = if (pendingOrdersCount > 0) LuxuryPalette.Terracotta else LuxuryPalette.ForestOlive
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Global Management Console",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = (-0.3).sp,
                        color = LuxuryPalette.Onyx
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Review incoming client transfers, curate the botanical catalog, and configure direct settlement channels.",
                        fontSize = 12.sp,
                        color = LuxuryPalette.TextMuted,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- 2. MANAGEMENT MODULES ---
            Text(
                text = "MANAGEMENT MODULES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.6.sp,
                color = LuxuryPalette.Onyx
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Module 1: Orders
            AdminHubNavigationCard(
                icon = Icons.Outlined.ReceiptLong,
                title = "Client Orders & Vouchers",
                subtitle = "Validate payment receipts, approve packaging, and inspect client purchases",
                badgeText = if (pendingOrdersCount > 0) "$pendingOrdersCount Pending" else null,
                onClick = onNavigateToOrders
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Module 2: Edit Products
            AdminHubNavigationCard(
                icon = Icons.Outlined.Inventory2,
                title = "Catalog & Curation",
                subtitle = "Manage active botanical extractions, limited gift sets, pricing, and imagery",
                badgeText = null,
                onClick = onNavigateToEditProducts
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Module 3: Payment Configuration
            AdminHubNavigationCard(
                icon = Icons.Outlined.Payments,
                title = "Payment Channels & QR",
                subtitle = "Configure direct mobile settlement, update Yape/Plin numbers, and upload official QR codes",
                badgeText = null,
                onClick = onNavigateToUpdatePayment
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Security note
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Authenticated Session • High-Priority Atelier Access • Changes reflect in real-time across customer clients.",
                    fontSize = 11.sp,
                    color = LuxuryPalette.TextCaption,
                    lineHeight = 16.sp
                )
            }
        }
    }
}

@Composable
private fun AdminHubNavigationCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badgeText: String?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LuxuryPalette.PureWhite)
            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = LuxuryPalette.Onyx,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = LuxuryPalette.Onyx,
                        letterSpacing = (-0.2).sp
                    )

                    if (badgeText != null) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(LuxuryPalette.Onyx)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = badgeText.uppercase(),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = LuxuryPalette.PureWhite
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = LuxuryPalette.TextMuted,
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = LuxuryPalette.TextCaption,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}