package com.ecommerce.app.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 🌿 LUXURY FOOTER & ATELIER PORTAL (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@Composable
fun FooterSection(
    modifier: Modifier = Modifier,
    onAdminLoginClick: () -> Unit
) {
    var isAdminExpanded by remember { mutableStateOf(false) }
    var emailAdmin by remember { mutableStateOf("") }
    var passwordAdmin by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(LuxuryPalette.Onyx)
            .padding(horizontal = 24.dp, vertical = 40.dp)
    ) {
        // Brand Title
        Text(
            text = "REMEDIOZ NATURA",
            color = LuxuryPalette.PureWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.5.sp
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Brand Manifesto
        Text(
            text = "Handcrafted organic formulations, cold-pressed active botanicals, and restorative rituals designed for conscious everyday luxury.",
            color = Color(0xFFA5A29D),
            fontSize = 13.sp,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(28.dp))
        HorizontalDivider(color = Color(0xFF262626), thickness = 1.dp)
        Spacer(modifier = Modifier.height(24.dp))

        // Three Pillars of Quality
        val pillars = listOf(
            "100% PURE BOTANICAL ACTIVE INGREDIENTS",
            "CRUELTY-FREE & SUSTAINABLY HARVESTED",
            "CLIMATE-NEUTRAL ECO PACKAGING"
        )
        pillars.forEach { pillar ->
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(LuxuryPalette.Champagne)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = pillar,
                    color = Color(0xFFC7C3BD),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.2.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(color = Color(0xFF262626), thickness = 1.dp)
        Spacer(modifier = Modifier.height(24.dp))

        // Editorial Menu Links
        Text(
            text = "CUSTOMER CARE & ATELIER",
            color = LuxuryPalette.PureWhite,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.6.sp
        )
        Spacer(modifier = Modifier.height(12.dp))

        val menuItems = listOf("Shipping & Delivery", "Sustainable Sourcing", "Ritual Consultations", "Terms & Atelier Policies")
        menuItems.forEach { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Editorial navigation */ }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = item,
                    color = Color(0xFF8E8B85),
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Discrete Admin Portal Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .clickable { isAdminExpanded = !isAdminExpanded }
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MANAGEMENT & ATELIER PORTAL",
                color = Color(0xFF706E69),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.4.sp
            )

            Icon(
                imageVector = if (isAdminExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Toggle Portal",
                tint = Color(0xFF706E69),
                modifier = Modifier.size(20.dp)
            )
        }

        // Expandable Admin Panel
        AnimatedVisibility(
            visible = isAdminExpanded,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(Color(0xFF161616), RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFF2B2B2B), RoundedCornerShape(8.dp))
                    .padding(20.dp)
            ) {
                Text(
                    text = "ATELIER CREDENTIALS",
                    color = LuxuryPalette.Champagne,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.4.sp
                )
                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = emailAdmin,
                    onValueChange = { emailAdmin = it },
                    placeholder = { Text("admin@remedioznatura.com", color = Color(0xFF6B6864), fontSize = 13.sp) },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = LuxuryPalette.PureWhite,
                        unfocusedTextColor = LuxuryPalette.PureWhite,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedBorderColor = LuxuryPalette.Champagne,
                        unfocusedBorderColor = Color(0xFF333333),
                        cursorColor = LuxuryPalette.Champagne
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = passwordAdmin,
                    onValueChange = { passwordAdmin = it },
                    placeholder = { Text("Master Passkey", color = Color(0xFF6B6864), fontSize = 13.sp) },
                    shape = RoundedCornerShape(4.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = LuxuryPalette.PureWhite,
                        unfocusedTextColor = LuxuryPalette.PureWhite,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedBorderColor = LuxuryPalette.Champagne,
                        unfocusedBorderColor = Color(0xFF333333),
                        cursorColor = LuxuryPalette.Champagne
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onAdminLoginClick() },
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LuxuryPalette.PureWhite)
                ) {
                    Text(
                        text = "ACCESS DASHBOARD",
                        color = LuxuryPalette.Onyx,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.6.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Copyright
        Text(
            text = "© 2026 REMEDIOZ NATURA. ALL RIGHTS RESERVED.",
            color = Color(0xFF55534E),
            fontSize = 9.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 1.4.sp
        )
    }
}