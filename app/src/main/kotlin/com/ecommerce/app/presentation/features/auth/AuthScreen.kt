package com.ecommerce.app.presentation.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.theme.LuxuryPalette
import com.ecommerce.natura.R

/**
 * ============================================================================
 * 🔐 LUXURY AUTHENTICATION & ATELIER SIGN-IN (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    onClose: () -> Unit,
    onClientSuccess: () -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryPalette.PureWhite)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar / Close Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(LuxuryPalette.Alabaster)
                    .border(1.dp, LuxuryPalette.BorderSubtle, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = LuxuryPalette.Onyx,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 1. EDITORIAL BRAND HEADER ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Remedioz Natura",
                fontFamily = FontFamily(Font(R.font.imperial_script)),
                fontSize = 52.sp,
                color = LuxuryPalette.Onyx,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "ATELIER & BOTANICAL APOTHECARY",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.2.sp,
                color = LuxuryPalette.ForestOlive,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Holistic well-being, cold-pressed purity, and tailored botanical rituals.",
                color = LuxuryPalette.Onyx,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = (-0.3).sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sign in to synchronize your shopping bag across devices, track atelier orders, and receive private release notifications.",
                color = LuxuryPalette.TextMuted,
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // --- 2. AUTHENTICATION ACTION ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = LuxuryPalette.Onyx,
                    modifier = Modifier.size(32.dp),
                    strokeWidth = 2.dp
                )
            } else {
                FloatingAuthButton(
                    text = "Continue with Google",
                    onClick = { viewModel.loginAsClient(onClientSuccess) }
                )
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = LuxuryPalette.Terracotta,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "By accessing your atelier profile, you agree to our Terms of Sourcing and Botanical Care Privacy Policy.",
                color = LuxuryPalette.TextCaption,
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f, fill = false))
        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
fun FloatingAuthButton(
    text: String,
    logoSize: Dp = 20.dp,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(LuxuryPalette.PureWhite)
            .border(BorderStroke(1.dp, LuxuryPalette.BorderLight), RoundedCornerShape(6.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_google_logo),
                contentDescription = "Google Logo",
                modifier = Modifier.size(logoSize)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = LuxuryPalette.Onyx,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
        }
    }
}