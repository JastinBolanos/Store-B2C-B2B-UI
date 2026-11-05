package com.ecommerce.app.presentation.features.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ecommerce.app.presentation.theme.LuxuryPalette
import com.ecommerce.natura.R
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch

/**
 * ============================================================================
 * 💳 LUXURY CHECKOUT & SECURE PAYMENT (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    totalAmount: Double,
    isLoading: Boolean,
    qrUrl: String,
    phoneNumber: String,
    onBackClick: () -> Unit,
    onConfirmOrder: (ByteArray) -> Unit
) {
    var selectedVoucherBytes by remember { mutableStateOf<ByteArray?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberFilePickerLauncher(
        type = PickerType.Image,
        mode = PickerMode.Single,
        title = "Select payment confirmation voucher"
    ) { platformFile ->
        if (platformFile != null) {
            coroutineScope.launch {
                selectedVoucherBytes = platformFile.readBytes()
            }
        }
    }

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "ORDER VERIFICATION",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 2.sp,
                            color = LuxuryPalette.Onyx
                        )
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
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(LuxuryPalette.PureWhite)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(28.dp))

            // --- 1. AMOUNT SUMMARY ---
            Text(
                text = "TOTAL AMOUNT DUE",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.6.sp,
                color = LuxuryPalette.TextCaption
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "$${totalAmount.format(2)}",
                fontSize = 38.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.5).sp,
                color = LuxuryPalette.Onyx
            )

            Spacer(modifier = Modifier.height(28.dp))

            // --- 2. PAYMENT INSTRUCTIONS (YAPE / PLIN) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "DIRECT MOBILE TRANSFER",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        color = LuxuryPalette.ForestOlive
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Scan QR code or send payment to:",
                        color = LuxuryPalette.TextMuted,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // QR Code Frame
                    Box(
                        modifier = Modifier
                            .size(170.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(LuxuryPalette.PureWhite)
                            .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_pago_qr),
                            contentDescription = "Official Payment QR",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(4.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = phoneNumber,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = LuxuryPalette.Onyx
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Luciana Valdivia Montes • Remedioz Natura",
                        color = LuxuryPalette.TextCaption,
                        fontSize = 12.sp,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. VOUCHER UPLOAD ---
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "PROOF OF PAYMENT",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp,
                        color = LuxuryPalette.Onyx
                    )
                    if (selectedVoucherBytes != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = LuxuryPalette.ForestOlive,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ATTACHED",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = LuxuryPalette.ForestOlive
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(LuxuryPalette.Alabaster)
                        .border(
                            1.dp,
                            if (selectedVoucherBytes == null) LuxuryPalette.BorderLight else LuxuryPalette.ForestOlive,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { launcher.launch() },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedVoucherBytes != null) {
                        AsyncImage(
                            model = selectedVoucherBytes,
                            contentDescription = "Selected voucher",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Outlined.ReceiptLong,
                                contentDescription = null,
                                tint = LuxuryPalette.TextCaption,
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "UPLOAD PAYMENT VOUCHER",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.4.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tap here to select an image from your gallery",
                                color = LuxuryPalette.TextMuted,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // --- 4. SUBMIT ORDER BUTTON ---
            Button(
                onClick = {
                    if (selectedVoucherBytes != null) {
                        onConfirmOrder(selectedVoucherBytes!!)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = selectedVoucherBytes != null,
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LuxuryPalette.Onyx,
                    disabledContainerColor = LuxuryPalette.BorderLight
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = LuxuryPalette.PureWhite,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = if (selectedVoucherBytes != null) "CONFIRM ORDER & TRANSMIT VOUCHER" else "ATTACH VOUCHER TO COMPLETE",
                        color = if (selectedVoucherBytes != null) LuxuryPalette.PureWhite else LuxuryPalette.TextCaption,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 1.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
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