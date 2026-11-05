package com.ecommerce.app.presentation.features.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ecommerce.app.presentation.theme.LuxuryPalette
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch

/**
 * ============================================================================
 * 🏦 LUXURY PAYMENT CONFIGURATION (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePaymentScreen(
    currentQrUrl: String,
    currentPhoneNumber: String,
    isLoading: Boolean,
    onBackClick: () -> Unit,
    onSaveClick: (ByteArray?, String) -> Unit
) {
    var selectedQrBytes by remember { mutableStateOf<ByteArray?>(null) }
    var phoneNumberText by remember(currentPhoneNumber) { mutableStateOf(currentPhoneNumber) }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberFilePickerLauncher(
        type = PickerType.Image,
        mode = PickerMode.Single,
        title = "Select official Yape/Plin QR code"
    ) { platformFile ->
        if (platformFile != null) {
            coroutineScope.launch {
                selectedQrBytes = platformFile.readBytes()
            }
        }
    }

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "PAYMENT CHANNELS",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Text(
                                text = "YAPE & PLIN CONFIGURATION",
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
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Notice banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Any modifications made to the official QR code or settlement phone number will instantly update for customers at checkout.",
                    color = LuxuryPalette.TextMuted,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Section label
            Text(
                text = "OFFICIAL SETTLEMENT QR",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.6.sp,
                color = LuxuryPalette.Onyx
            )
            Spacer(modifier = Modifier.height(12.dp))

            // QR Frame
            Box(
                modifier = Modifier
                    .size(210.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LuxuryPalette.Alabaster)
                    .border(
                        width = 1.dp,
                        color = if (selectedQrBytes == null) LuxuryPalette.BorderLight else LuxuryPalette.ForestOlive,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable { launcher.launch() },
                contentAlignment = Alignment.Center
            ) {
                if (selectedQrBytes != null) {
                    AsyncImage(
                        model = selectedQrBytes,
                        contentDescription = "New selected QR",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
                    )
                } else if (currentQrUrl.isNotEmpty()) {
                    AsyncImage(
                        model = currentQrUrl,
                        contentDescription = "Current QR",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Outlined.QrCodeScanner,
                            contentDescription = null,
                            tint = LuxuryPalette.TextCaption,
                            modifier = Modifier.size(44.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "UPLOAD NEW QR",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp,
                            color = LuxuryPalette.Onyx
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Tap to select image",
                            color = LuxuryPalette.TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Phone Input
            OutlinedTextField(
                value = phoneNumberText,
                onValueChange = { phoneNumberText = it },
                label = { Text("Settlement Number (Yape / Plin)", fontSize = 12.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LuxuryPalette.Alabaster,
                    unfocusedContainerColor = LuxuryPalette.Alabaster,
                    focusedBorderColor = LuxuryPalette.Onyx,
                    unfocusedBorderColor = LuxuryPalette.BorderLight,
                    cursorColor = LuxuryPalette.Onyx
                )
            )

            Spacer(modifier = Modifier.height(36.dp))

            val hasChanges = selectedQrBytes != null || phoneNumberText != currentPhoneNumber

            // Save Button
            Button(
                onClick = {
                    if (hasChanges && !isLoading) {
                        onSaveClick(selectedQrBytes, phoneNumberText)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = hasChanges && !isLoading,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LuxuryPalette.Onyx,
                    disabledContainerColor = LuxuryPalette.BorderLight
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = LuxuryPalette.PureWhite, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                } else {
                    Text(
                        text = if (hasChanges) "SAVE SETTLEMENT CONFIGURATION" else "NO CHANGES DETECTED",
                        color = if (hasChanges) LuxuryPalette.PureWhite else LuxuryPalette.TextCaption,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 11.sp,
                        letterSpacing = 1.4.sp
                    )
                }
            }
        }
    }
}