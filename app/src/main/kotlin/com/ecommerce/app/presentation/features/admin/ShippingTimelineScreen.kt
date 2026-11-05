package com.ecommerce.app.presentation.features.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
 * 🚚 LUXURY ADMIN FULFILLMENT & SHIPPING TIMELINE (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShippingTimelineScreen(
    orderId: String,
    customerName: String,
    currentStatus: String,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onUpdateStatusClick: (String) -> Unit
) {
    val statusFlow = listOf(
        "Approved" to "Payment verified by finance team",
        "Preparing Package" to "Cold-pressed extractions bottled in glass vials",
        "In Transit" to "Handed over to climate-neutral courier",
        "Delivered" to "Successfully received by client"
    )

    val currentIndex = statusFlow.indexOfFirst { it.first.equals(currentStatus, ignoreCase = true) }.takeIf { it >= 0 } ?: 0
    var showConfirmDialog by remember { mutableStateOf(false) }
    var pendingNextStatus by remember { mutableStateOf("") }

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "FULFILLMENT MILESTONES",
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
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            // --- 1. ORDER DETAILS SUMMARY ---
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
                            text = "DISPATCH MANAGEMENT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.4.sp,
                            color = LuxuryPalette.ForestOlive
                        )

                        val isDelivered = currentStatus.equals("Delivered", ignoreCase = true)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isDelivered) LuxuryPalette.ForestOliveLight else LuxuryPalette.Onyx)
                                .border(
                                    1.dp,
                                    if (isDelivered) LuxuryPalette.ForestOlive.copy(alpha = 0.3f) else LuxuryPalette.Onyx,
                                    RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = currentStatus.uppercase(),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = if (isDelivered) LuxuryPalette.ForestOlive else LuxuryPalette.PureWhite
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
                            Text("ORDER ID", fontSize = 9.sp, letterSpacing = 1.sp, color = LuxuryPalette.TextCaption)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(orderId.uppercase(), fontSize = 12.sp, fontWeight = FontWeight.Medium, color = LuxuryPalette.Onyx)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("CLIENT", fontSize = 9.sp, letterSpacing = 1.sp, color = LuxuryPalette.TextCaption)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(customerName, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = LuxuryPalette.Onyx)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // --- 2. VERTICAL TIMELINE ---
            Text(
                text = "LOGISTICS TIMELINE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.8.sp,
                color = LuxuryPalette.Onyx
            )
            Spacer(modifier = Modifier.height(20.dp))

            statusFlow.forEachIndexed { index, (stepTitle, stepDesc) ->
                val isCompleted = index <= currentIndex
                val isLast = index == statusFlow.lastIndex
                val isActive = index == currentIndex

                AdminTimelineStepRow(
                    title = stepTitle,
                    description = stepDesc,
                    isCompleted = isCompleted,
                    isActive = isActive,
                    isLast = isLast
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. MUTATION CTA ---
            if (currentIndex < statusFlow.lastIndex) {
                val nextStatus = statusFlow[currentIndex + 1].first

                Button(
                    onClick = {
                        pendingNextStatus = nextStatus
                        showConfirmDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(4.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = LuxuryPalette.Onyx)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = LuxuryPalette.PureWhite, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text(
                            text = "ADVANCE TO: ${nextStatus.uppercase()}",
                            color = LuxuryPalette.PureWhite,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            letterSpacing = 1.4.sp
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(LuxuryPalette.ForestOliveLight)
                        .border(1.dp, LuxuryPalette.ForestOlive.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = LuxuryPalette.ForestOlive, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ORDER DELIVERED & CONCLUDED",
                            color = LuxuryPalette.ForestOlive,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 1.4.sp
                        )
                    }
                }
            }
        }

        // CONFIRMATION DIALOG IN LUXURY STYLING
        if (showConfirmDialog) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                containerColor = LuxuryPalette.PureWhite,
                shape = RoundedCornerShape(8.dp),
                title = {
                    Text(
                        text = "CONFIRM STATUS MUTATION",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        letterSpacing = 1.2.sp,
                        color = LuxuryPalette.Onyx
                    )
                },
                text = {
                    Text(
                        text = "Advance this order to \"$pendingNextStatus\"?\n\nThis will instantly update the client's profile tracking timeline.",
                        color = LuxuryPalette.TextMuted,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showConfirmDialog = false
                            onUpdateStatusClick(pendingNextStatus)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = LuxuryPalette.Onyx),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "ADVANCE",
                            color = LuxuryPalette.PureWhite,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                },
                dismissButton = {
                    OutlinedButton(
                        onClick = { showConfirmDialog = false },
                        border = BorderStroke(1.dp, LuxuryPalette.BorderLight),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "CANCEL",
                            color = LuxuryPalette.Onyx,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun AdminTimelineStepRow(
    title: String,
    description: String,
    isCompleted: Boolean,
    isActive: Boolean,
    isLast: Boolean
) {
    val indicatorColor = when {
        isActive -> LuxuryPalette.Onyx
        isCompleted -> LuxuryPalette.ForestOlive
        else -> LuxuryPalette.BorderLight
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(if (isCompleted) indicatorColor else LuxuryPalette.PureWhite)
                    .border(
                        1.5.dp,
                        if (isActive) LuxuryPalette.Onyx else indicatorColor,
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
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
                        .background(if (isCompleted && !isActive) LuxuryPalette.ForestOlive else LuxuryPalette.BorderLight)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

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