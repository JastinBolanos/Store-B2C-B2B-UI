package com.ecommerce.app.presentation.features.profile

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 📦 LUXURY ORDER TRACKING & DISPATCH TIMELINE (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerTimelineScreen(
    orderId: String,
    currentStatus: String,
    onBackClick: () -> Unit
) {
    val statusFlow = listOf(
        "Pending" to "Order registered in atelier database",
        "Approved" to "Payment verified by finance team",
        "Preparing Package" to "Cold-pressed extractions bottled in glass vials",
        "In Transit" to "Handed over to climate-neutral courier",
        "Delivered" to "Successfully received by client"
    )

    val isRejected = currentStatus.equals("Rejected", ignoreCase = true)
    val currentIndex = if (isRejected) 0 else statusFlow.indexOfFirst { it.first.equals(currentStatus, ignoreCase = true) }.takeIf { it >= 0 } ?: 0

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "PARCEL TRACKING",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Text(
                                text = "ORDER #${orderId.takeLast(6).uppercase()}",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LuxuryPalette.PureWhite)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {
            // --- 1. ORDER SUMMARY CARD ---
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
                        Column {
                            Text(
                                text = "LOGISTICS ATELIER",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.4.sp,
                                color = LuxuryPalette.ForestOlive
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "CLIMATE-NEUTRAL DISPATCH",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.5.sp,
                                color = LuxuryPalette.Onyx
                            )
                        }

                        // Status Pill
                        val (pillBg, pillText) = when {
                            currentStatus.equals("Delivered", ignoreCase = true) -> LuxuryPalette.ForestOliveLight to LuxuryPalette.ForestOlive
                            isRejected -> LuxuryPalette.TerracottaLight to LuxuryPalette.Terracotta
                            else -> LuxuryPalette.Onyx to LuxuryPalette.PureWhite
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(pillBg)
                                .border(1.dp, pillText.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = currentStatus.uppercase(),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = pillText
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(color = LuxuryPalette.BorderSubtle, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("ORDER REFERENCE", fontSize = 9.sp, letterSpacing = 1.sp, color = LuxuryPalette.TextCaption)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(orderId.uppercase(), fontSize = 12.sp, fontWeight = FontWeight.Medium, color = LuxuryPalette.Onyx)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("COURIER SERVICE", fontSize = 9.sp, letterSpacing = 1.sp, color = LuxuryPalette.TextCaption)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text("Eco-Express Natura", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = LuxuryPalette.Onyx)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // --- 2. EDITORIAL VERTICAL TIMELINE ---
            Text(
                text = "FULFILLMENT MILESTONES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp,
                color = LuxuryPalette.Onyx
            )
            Spacer(modifier = Modifier.height(20.dp))

            statusFlow.forEachIndexed { index, (stepTitle, stepDesc) ->
                val isCompleted = index <= currentIndex && !isRejected
                val isLast = index == statusFlow.lastIndex
                val isActive = index == currentIndex && !isRejected
                val isStepRejected = isRejected && index == 0

                LuxuryTimelineStepRow(
                    title = stepTitle,
                    description = stepDesc,
                    isCompleted = isCompleted,
                    isActive = isActive,
                    isLast = isLast,
                    isRejected = isStepRejected
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. CONTEXTUAL STATUS BANNER ---
            if (currentStatus.equals("Delivered", ignoreCase = true)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(LuxuryPalette.ForestOliveLight)
                        .border(1.dp, LuxuryPalette.ForestOlive.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "PARCEL DELIVERED TO CLIENT",
                            color = LuxuryPalette.ForestOlive,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 1.4.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Thank you for welcoming our organic formulations into your daily ritual.",
                            color = LuxuryPalette.ForestOlive.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }
            } else if (isRejected) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(LuxuryPalette.TerracottaLight)
                        .border(1.dp, LuxuryPalette.Terracotta.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "ORDER CANCELLED OR INCOMPLETE",
                            color = LuxuryPalette.Terracotta,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 1.4.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "The payment voucher could not be verified. Please contact your personal concierge for immediate resolution.",
                            color = LuxuryPalette.Terracotta.copy(alpha = 0.85f),
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(LuxuryPalette.Alabaster)
                        .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(6.dp))
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocalShipping,
                            contentDescription = null,
                            tint = LuxuryPalette.Onyx,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Our atelier will transmit tracking notifications as each preparation milestone is verified.",
                            color = LuxuryPalette.TextMuted,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * Editorial timeline row with connected vertical rail
 */
@Composable
private fun LuxuryTimelineStepRow(
    title: String,
    description: String,
    isCompleted: Boolean,
    isActive: Boolean,
    isLast: Boolean,
    isRejected: Boolean
) {
    val indicatorColor = when {
        isRejected -> LuxuryPalette.Terracotta
        isActive -> LuxuryPalette.Onyx
        isCompleted -> LuxuryPalette.ForestOlive
        else -> LuxuryPalette.BorderLight
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Vertical rail + indicator node
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        if (isCompleted || isRejected) indicatorColor else LuxuryPalette.PureWhite
                    )
                    .border(
                        1.5.dp,
                        if (isActive) LuxuryPalette.Onyx else indicatorColor,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted && !isRejected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = LuxuryPalette.PureWhite,
                        modifier = Modifier.size(12.dp)
                    )
                } else if (isRejected) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = null,
                        tint = LuxuryPalette.PureWhite,
                        modifier = Modifier.size(12.dp)
                    )
                } else if (isActive) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(LuxuryPalette.Onyx)
                    )
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(1.5.dp)
                        .weight(1f)
                        .background(if (isCompleted && !isActive && !isRejected) LuxuryPalette.ForestOlive else LuxuryPalette.BorderLight)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Textual step details
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 24.dp)
        ) {
            Text(
                text = title.uppercase(),
                fontSize = 12.sp,
                fontWeight = if (isActive || isCompleted) FontWeight.SemiBold else FontWeight.Medium,
                letterSpacing = 1.sp,
                color = if (isCompleted || isActive) LuxuryPalette.Onyx else LuxuryPalette.TextCaption
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 11.sp,
                color = LuxuryPalette.TextMuted,
                lineHeight = 16.sp
            )
        }
    }
}