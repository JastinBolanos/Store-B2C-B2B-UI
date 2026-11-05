package com.ecommerce.app.presentation.features.admin

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
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.domain.model.Order
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 🔔 LUXURY ADMIN NOTIFICATIONS & ALERTS (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    pendingOrders: List<Order>,
    onBackClick: () -> Unit,
    onNotificationClick: (Order) -> Unit
) {
    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "ATELIER ALERTS",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Text(
                                text = "${pendingOrders.size} PENDING ACTIONS",
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
                HorizontalDivider(color = LuxuryPalette.BorderSubtle, thickness = 1.dp)
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
            items(pendingOrders, key = { it.id }) { order ->
                LuxuryNotificationItem(
                    order = order,
                    onClick = { onNotificationClick(order) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (pendingOrders.isEmpty()) {
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
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = null,
                                tint = LuxuryPalette.BorderLight,
                                modifier = Modifier.size(44.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "INBOX IS UP TO DATE",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.6.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "No unconfirmed customer payments or urgent logistics alerts.",
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
fun LuxuryNotificationItem(order: Order, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LuxuryPalette.PureWhite)
            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Elegant Status Dot
            Box(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(LuxuryPalette.Terracotta)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "NEW SETTLEMENT #${order.id.takeLast(6).uppercase()}",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.Onyx
                    )
                    Text(
                        text = formatOrderTime(order.timestamp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp,
                        color = LuxuryPalette.TextCaption
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${order.customerName} has uploaded a payment voucher for $${order.totalAmount.format(2)}.",
                    fontSize = 13.sp,
                    color = LuxuryPalette.TextMuted,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "REVIEW & AUTHORIZE",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = LuxuryPalette.ForestOlive
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = LuxuryPalette.ForestOlive,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
        }
    }
}

fun formatOrderTime(millis: Long): String {
    if (millis == 0L) return "Just now"

    val totalSeconds = millis / 1000
    val currentSeconds = totalSeconds % (24 * 3600)

    val hours = ((currentSeconds / 3600) - 5 + 24) % 24
    val minutes = (currentSeconds % 3600) / 60

    val amPm = if (hours >= 12) "PM" else "AM"
    val displayHour = if (hours % 12 == 0L) 12L else hours % 12
    val minStr = if (minutes < 10) "0$minutes" else "$minutes"

    return "$displayHour:$minStr $amPm"
}

private fun Double.format(digits: Int): String {
    val rounded = (this * 100).toLong() / 100.0
    val parts = rounded.toString().split(".")
    val whole = parts[0]
    val fraction = if (parts.size > 1) parts[1] else "0"
    return "$whole.${fraction.padEnd(digits, '0')}"
}