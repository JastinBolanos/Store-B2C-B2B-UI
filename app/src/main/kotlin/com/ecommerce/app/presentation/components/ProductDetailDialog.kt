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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.ecommerce.app.domain.model.Product
import com.ecommerce.app.presentation.state.CartManager
import com.ecommerce.app.presentation.theme.LuxuryPalette
import kotlinx.coroutines.delay

/**
 * ============================================================================
 * 🛍️ LUXURY PRODUCT DETAIL MODAL (COMPACT WRAP-CONTENT TRAY)
 * ============================================================================
 */
@Composable
fun ProductDetailDialog(
    product: Product,
    imagePainter: Painter,
    onDismiss: () -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    var addedToBag by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "dialog_ambient_border")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ambient_border_angle"
    )

    LaunchedEffect(addedToBag) {
        if (addedToBag) {
            delay(1600)
            addedToBag = false
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        // Outer Container: Wraps exact content height with reasonable ceiling to prevent wasted empty space
        Box(
            modifier = Modifier
                .fillMaxWidth(0.90f)
                .wrapContentHeight()
                .heightIn(max = 700.dp)
                .clip(RoundedCornerShape(16.dp))
                .drawBehind {
                    rotate(angle) {
                        drawCircle(
                            brush = Brush.sweepGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    LuxuryPalette.Champagne.copy(alpha = 0.5f),
                                    LuxuryPalette.BorderLight,
                                    LuxuryPalette.Onyx.copy(alpha = 0.35f),
                                    Color.Transparent
                                )
                            ),
                            radius = size.maxDimension
                        )
                    }
                }
                .padding(1.5.dp)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(15.dp),
                color = LuxuryPalette.PureWhite
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    // Floating Close Button (Top End)
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(36.dp)
                            .background(LuxuryPalette.PureWhite.copy(alpha = 0.92f), CircleShape)
                            .border(1.dp, LuxuryPalette.BorderLight, CircleShape)
                            .zIndex(2f)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = LuxuryPalette.Onyx,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Product Hero Image - Original 320.dp Height
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(320.dp)
                                .background(LuxuryPalette.Alabaster)
                        ) {
                            Image(
                                painter = imagePainter,
                                contentDescription = product.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            // Category / Certification pill
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(14.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(LuxuryPalette.PureWhite.copy(alpha = 0.94f))
                                    .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(4.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = product.category.uppercase(),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.4.sp,
                                    color = LuxuryPalette.ForestOlive
                                )
                            }
                        }

                        // Product Details Section - Compact & Tight
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 18.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = product.name,
                                        fontSize = 19.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        letterSpacing = (-0.3).sp,
                                        color = LuxuryPalette.Onyx,
                                        lineHeight = 24.sp
                                    )
                                    Spacer(modifier = Modifier.height(3.dp))
                                    Text(
                                        text = "100% PURE BOTANICAL EXTRACT",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 1.1.sp,
                                        color = LuxuryPalette.TextCaption
                                    )
                                }

                                Text(
                                    text = "$${product.price.format(2)}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = LuxuryPalette.Onyx
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(LuxuryPalette.BorderSubtle)
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = "THE FORMULATION",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.6.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = product.description.ifEmpty {
                                    "Crafted with clinically proven, cold-pressed botanicals to restore balance, cellular hydration, and natural vitality."
                                },
                                fontSize = 13.sp,
                                color = LuxuryPalette.TextMuted,
                                lineHeight = 19.sp
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Quantity Stepper + Add to Bag Button
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Stepper
                                Row(
                                    modifier = Modifier
                                        .height(46.dp)
                                        .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(4.dp))
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(LuxuryPalette.Alabaster),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = { if (quantity > 1) quantity-- },
                                        modifier = Modifier.size(42.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Remove,
                                            contentDescription = "Decrease",
                                            tint = LuxuryPalette.Onyx,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }

                                    Text(
                                        text = quantity.toString(),
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = LuxuryPalette.Onyx,
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )

                                    IconButton(
                                        onClick = { quantity++ },
                                        modifier = Modifier.size(42.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Increase",
                                            tint = LuxuryPalette.Onyx,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }
                                }

                                // Add to Bag Button
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(46.dp)
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(if (addedToBag) LuxuryPalette.ForestOlive else LuxuryPalette.Onyx)
                                        .clickable {
                                            CartManager.addProduct(product, quantity)
                                            addedToBag = true
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        if (addedToBag) {
                                            Icon(
                                                Icons.Default.Check,
                                                contentDescription = null,
                                                tint = LuxuryPalette.PureWhite,
                                                modifier = Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "ADDED TO BAG",
                                                color = LuxuryPalette.PureWhite,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                letterSpacing = 1.2.sp
                                            )
                                        } else {
                                            Icon(
                                                Icons.Outlined.ShoppingBag,
                                                contentDescription = null,
                                                tint = LuxuryPalette.PureWhite,
                                                modifier = Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Text(
                                                text = "ADD TO BAG • $${(product.price * quantity).format(2)}",
                                                color = LuxuryPalette.PureWhite,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                letterSpacing = 1.2.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}