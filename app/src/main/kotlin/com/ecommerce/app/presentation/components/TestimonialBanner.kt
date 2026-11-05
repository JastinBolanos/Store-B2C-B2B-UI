package com.ecommerce.app.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.theme.LuxuryPalette
import com.ecommerce.natura.R

/**
 * ============================================================================
 * 💫 LUXURY EDITORIAL CAMPAIGN BANNER (MANGO VIBE WITH GPU ROTATING GLOW)
 * ============================================================================
 */
@Composable
fun TestimonialBanner(modifier: Modifier = Modifier) {
    // --- INFINITE ROTATION GLOW ---
    val infiniteTransition = rememberInfiniteTransition(label = "banner_ambient_glow")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "banner_angle"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(390.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        // --- LAYER 1: GPU-ACCELERATED AMBIENT GLOW ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    rotate(angle) {
                        drawCircle(
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Transparent,
                                    LuxuryPalette.Champagne.copy(alpha = 0.6f),
                                    LuxuryPalette.PureWhite,
                                    LuxuryPalette.Onyx,
                                    Color.Transparent,
                                    Color.Transparent
                                )
                            ),
                            radius = size.width
                        )
                    }
                }
        )

        // --- LAYER 2: HERO CAMPAIGN IMAGE & EDITORIAL OVERLAY ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.2.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(LuxuryPalette.Onyx)
        ) {
            Image(
                painter = painterResource(R.drawable.img_banner_full),
                contentDescription = "Botanical Specialist Campaign",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // High-Fashion Gradient Scrim for readable editorial typography
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.2f),
                                Color.Black.copy(alpha = 0.78f)
                            ),
                            startY = 150f
                        )
                    )
            )

            // Editorial Typography Overlay
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
            ) {


                Text(
                    text = "Harmonious Alchemy",
                    fontFamily = FontFamily(Font(R.font.imperial_script)),
                    fontSize = 38.sp,
                    fontWeight = FontWeight.Normal,
                    color = LuxuryPalette.PureWhite
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Pure biological extracts harvested with artisanal reverence. Formulated to elevate everyday rituals.",
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = LuxuryPalette.PureWhite.copy(alpha = 0.85f),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .border(1.dp, LuxuryPalette.PureWhite.copy(alpha = 0.8f), RoundedCornerShape(2.dp))
                        .padding(horizontal = 14.dp, vertical = 7.dp)
                ) {
                    Text(
                        text = "EXPLORE RITUALS",
                        color = LuxuryPalette.PureWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )
                }
            }
        }
    }
}