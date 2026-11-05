package com.ecommerce.app.presentation.features.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.theme.LuxuryPalette
import com.ecommerce.natura.R

/**
 * ============================================================================
 * ⚖️ LUXURY ADMIN ORDER VERDICT & VOUCHER REVIEW (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsScreen(
    orderId: String,
    voucherUrl: String,
    customerName: String,
    totalAmount: String,
    status: String,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onApproveClick: (String) -> Unit,
    onRejectClick: (String) -> Unit
) {
    val formattedTotal = totalAmount.replace("$ ", "").toDoubleOrNull()?.format(2) ?: "0.00"
    val isPending = status.equals("Pending", ignoreCase = true)
    val isApproved = status.equals("Approved", ignoreCase = true)
    val isRejected = status.equals("Rejected", ignoreCase = true)

    val (channelName, channelTag) = when {
        voucherUrl.contains("yape", ignoreCase = true) -> "YAPE (BCP) MOBILE SETTLEMENT" to "INSTANT MOBILE TRANSFER"
        voucherUrl.contains("plin", ignoreCase = true) -> "PLIN INTERBANK SETTLEMENT" to "QR DIGITAL SETTLEMENT"
        voucherUrl.contains("bcp", ignoreCase = true) -> "BANCO DE CRÉDITO DEL PERÚ" to "DIRECT WIRE TRANSFER"
        else -> "OFFICIAL PAYMENT CHANNEL" to "P2P DIGITAL RECEIPT"
    }

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "PAYMENT VERDICT",
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
            // --- 1. VOUCHER INSPECTION HEADER ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CLIENT PAYMENT VOUCHER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.6.sp,
                        color = LuxuryPalette.Onyx
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = channelName,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.ForestOlive
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(LuxuryPalette.ForestOliveLight)
                        .border(1.dp, LuxuryPalette.ForestOlive.copy(alpha = 0.25f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = channelTag,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.ForestOlive
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // --- 2. AUTHENTIC VOUCHER INSPECTION FRAME ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                    .padding(14.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Digital Transaction Metadata Header
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LuxuryPalette.PureWhite, RoundedCornerShape(6.dp))
                            .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp))
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.ReceiptLong,
                                contentDescription = null,
                                tint = LuxuryPalette.Onyx,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "OP #0849${orderId.takeLast(4)}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = LuxuryPalette.Onyx
                            )
                        }

                        Text(
                            text = "$ $formattedTotal USD",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = LuxuryPalette.ForestOlive
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Official Voucher Image from App Resources
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(340.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(LuxuryPalette.PureWhite)
                            .border(1.dp, LuxuryPalette.BorderSubtle, RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_voucher),
                            contentDescription = "Official customer voucher receipt",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sub-proof watermark: Beneficiary
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Beneficiary: Luciana Valdivia Montes",
                            fontSize = 10.sp,
                            color = LuxuryPalette.TextCaption
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = LuxuryPalette.ForestOlive,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "DIGITAL RECEIPT TRANSMITTED",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.8.sp,
                                color = LuxuryPalette.ForestOlive
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- 3. ORDER SPECIFICATIONS CARD ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                    .padding(20.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "ORDER SPECIFICATIONS",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.4.sp,
                        color = LuxuryPalette.ForestOlive
                    )
                    Spacer(modifier = Modifier.height(14.dp))

                    LuxuryDetailRow("Order Reference:", orderId.uppercase())
                    Spacer(modifier = Modifier.height(8.dp))
                    LuxuryDetailRow("Client Name:", customerName)
                    Spacer(modifier = Modifier.height(8.dp))
                    LuxuryDetailRow("Settlement Total:", "$ $formattedTotal", isHighlight = true)
                    Spacer(modifier = Modifier.height(8.dp))

                    val statusCol = when {
                        isApproved -> LuxuryPalette.ForestOlive
                        isRejected -> LuxuryPalette.Terracotta
                        else -> LuxuryPalette.Onyx
                    }
                    LuxuryDetailRow("Current Status:", status.uppercase(), valueColor = statusCol)
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // --- 4. VERDICT CONTROLS ---
            if (isPending) {
                Button(
                    onClick = { onApproveClick(orderId) },
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = LuxuryPalette.PureWhite, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "APPROVE PAYMENT & INITIATE DISPATCH",
                                color = LuxuryPalette.PureWhite,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp,
                                letterSpacing = 1.4.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { onRejectClick(orderId) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(4.dp),
                    enabled = !isLoading,
                    border = BorderStroke(1.dp, LuxuryPalette.Terracotta.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = LuxuryPalette.Terracotta)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = LuxuryPalette.Terracotta, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = LuxuryPalette.Terracotta, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "REJECT (INVALID PROOF)",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp,
                                letterSpacing = 1.4.sp,
                                color = LuxuryPalette.Terracotta
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isApproved) LuxuryPalette.ForestOliveLight else LuxuryPalette.TerracottaLight)
                        .border(
                            1.dp,
                            if (isApproved) LuxuryPalette.ForestOlive.copy(alpha = 0.3f) else LuxuryPalette.Terracotta.copy(alpha = 0.3f),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isApproved) "THIS ORDER HAS BEEN APPROVED AND ROUTED TO DISPATCH." else "THIS ORDER HAS BEEN FORMALLY REJECTED.",
                        color = if (isApproved) LuxuryPalette.ForestOlive else LuxuryPalette.Terracotta,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun LuxuryDetailRow(
    label: String,
    value: String,
    valueColor: Color = LuxuryPalette.Onyx,
    isHighlight: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = LuxuryPalette.TextMuted,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = if (isHighlight) 14.sp else 12.sp,
            fontWeight = if (isHighlight) FontWeight.Bold else FontWeight.Medium
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