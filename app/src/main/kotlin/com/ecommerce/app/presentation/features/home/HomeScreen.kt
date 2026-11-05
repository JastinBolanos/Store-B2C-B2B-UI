package com.ecommerce.app.presentation.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ecommerce.app.domain.model.CartItem
import com.ecommerce.app.presentation.components.BackHandler
import com.ecommerce.app.presentation.components.CategoryFilter
import com.ecommerce.app.presentation.components.FooterSection
import com.ecommerce.app.presentation.components.LandscapeProductCard
import com.ecommerce.app.presentation.components.ProductCard
import com.ecommerce.app.presentation.components.SearchInput
import com.ecommerce.app.presentation.components.TestimonialBanner
import com.ecommerce.app.presentation.components.TopNavBar
import com.ecommerce.app.presentation.features.cart.CartScreen
import com.ecommerce.app.presentation.features.checkout.CheckoutScreen
import com.ecommerce.app.presentation.features.checkout.CheckoutViewModel
import com.ecommerce.app.presentation.state.CartManager
import com.ecommerce.app.presentation.theme.LuxuryPalette

/**
 * ============================================================================
 * 🏠 LUXURY HOME & STOREFRONT (MANGO EDITORIAL EXPERIENCE)
 * ============================================================================
 */
@Composable
fun HomeScreen(
    onAdminClick: () -> Unit,
    onAuthClick: () -> Unit,
    viewModel: HomeViewModel,
    checkoutViewModel: CheckoutViewModel
) {
    var isSearchFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var currentScreen by remember { mutableStateOf("STORE") }
    var directPurchaseItem by remember { mutableStateOf<CartItem?>(null) }

    val filteredProducts by viewModel.filteredProducts.collectAsState()
    val rawKits by viewModel.kits.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val verticalProducts = filteredProducts

    val kitsProducts = rawKits.filter { kit ->
        val matchesQuery = kit.name.contains(searchQuery, ignoreCase = true) ||
                kit.description.contains(searchQuery, ignoreCase = true)
        val matchesCategory = selectedCategory == "All" || selectedCategory.equals("Kits", ignoreCase = true)

        matchesQuery && matchesCategory
    }

    when (currentScreen) {
        "STORE" -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LuxuryPalette.PureWhite)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { focusManager.clearFocus() }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 0.dp)
                ) {
                    item {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            TopNavBar(
                                onCartClick = { currentScreen = "CART" },
                                onProfileClick = { onAuthClick() }
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            SearchInput(
                                query = searchQuery,
                                onQueryChange = { viewModel.updateSearchQuery(it) },
                                isFocused = isSearchFocused,
                                onFocusChange = { isSearchFocused = it }
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            CategoryFilter(
                                selectedCategory = selectedCategory,
                                onCategorySelected = { viewModel.updateCategory(it) }
                            )
                            Spacer(modifier = Modifier.height(28.dp))
                        }
                    }

                    // Section 1: The Essential Collection
                    item {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Column {
                                    Text(
                                        text = "DAILY RADIANCE",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        letterSpacing = 1.6.sp,
                                        color = LuxuryPalette.ForestOlive
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "THE COLLECTION",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        letterSpacing = 1.2.sp,
                                        color = LuxuryPalette.Onyx
                                    )
                                }

                                if (verticalProducts.isNotEmpty()) {
                                    Text(
                                        text = "${verticalProducts.size} ITEMS",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 1.sp,
                                        color = LuxuryPalette.TextCaption
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            if (verticalProducts.isNotEmpty()) {
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 20.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(verticalProducts, key = { it.id }) { product ->
                                        ProductCard(
                                            product = product,
                                            modifier = Modifier.width(184.dp),
                                            onBuyNowClick = { qty ->
                                                directPurchaseItem = CartItem(product = product, quantity = qty)
                                                currentScreen = "CHECKOUT"
                                            }
                                        )
                                    }
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 24.dp)
                                        .background(LuxuryPalette.Alabaster, RoundedCornerShape(8.dp))
                                        .padding(24.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(
                                            text = "NO FORMULATIONS FOUND",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            letterSpacing = 1.4.sp,
                                            color = LuxuryPalette.Onyx
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "Try adjusting your search or category filter.",
                                            fontSize = 12.sp,
                                            color = LuxuryPalette.TextMuted
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Section 2: Curated Sets & Kits
                    item {
                        if (kitsProducts.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 36.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Column {
                                        Text(
                                            text = "ATELIER SELECTION",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            letterSpacing = 1.6.sp,
                                            color = LuxuryPalette.Champagne
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = "CURATED SETS & RITUALS",
                                            fontSize = 17.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            letterSpacing = 1.2.sp,
                                            color = LuxuryPalette.Onyx
                                        )
                                    }

                                    Text(
                                        text = "${kitsProducts.size} SETS",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 1.sp,
                                        color = LuxuryPalette.TextCaption
                                    )
                                }

                                Spacer(modifier = Modifier.height(14.dp))

                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 20.dp),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(kitsProducts, key = { it.id }) { product ->
                                        LandscapeProductCard(
                                            product = product,
                                            modifier = Modifier,
                                            onBuyNowClick = { qty ->
                                                directPurchaseItem = CartItem(product = product, quantity = qty)
                                                currentScreen = "CHECKOUT"
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Section 3: Testimonial & Campaign Banner + Luxury Footer
                    item {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(44.dp))
                            TestimonialBanner()
                            Spacer(modifier = Modifier.height(48.dp))
                            FooterSection(onAdminLoginClick = { onAdminClick() })
                        }
                    }
                }
            }
        }

        "CART" -> {
            BackHandler { currentScreen = "STORE" }

            CartScreen(
                onBackClick = { currentScreen = "STORE" },
                onProceedToCheckoutClick = {
                    directPurchaseItem = null
                    currentScreen = "CHECKOUT"
                },
                onDirectPurchaseClick = { itemToBuy ->
                    directPurchaseItem = itemToBuy
                    currentScreen = "CHECKOUT"
                }
            )
        }

        "CHECKOUT" -> {
            BackHandler {
                if (directPurchaseItem != null) {
                    directPurchaseItem = null
                    currentScreen = "CART"
                } else {
                    currentScreen = "CART"
                }
            }

            val isLoading by checkoutViewModel.isLoading.collectAsState()
            val orderSuccess by checkoutViewModel.orderSuccess.collectAsState()
            val paymentSettings by checkoutViewModel.paymentSettings.collectAsState()
            val itemsToBuy = if (directPurchaseItem != null) listOf(directPurchaseItem!!) else CartManager.cartItems.value

            val total = itemsToBuy.sumOf { item ->
                item.product.price * item.quantity
            }

            LaunchedEffect(orderSuccess) {
                if (orderSuccess) {
                    if (directPurchaseItem == null) {
                        CartManager.clearCart()
                    }
                    directPurchaseItem = null
                    checkoutViewModel.resetState()
                    currentScreen = "STORE"
                }
            }

            CheckoutScreen(
                totalAmount = total,
                isLoading = isLoading,
                qrUrl = "",
                phoneNumber = paymentSettings.yapeNumber,
                onBackClick = {
                    if (directPurchaseItem != null) {
                        directPurchaseItem = null
                        currentScreen = "CART"
                    } else {
                        currentScreen = "CART"
                    }
                },
                onConfirmOrder = { voucherBytes ->
                    checkoutViewModel.processOrder(
                        userId = "USER-ANONYMOUS",
                        customerName = "App Customer",
                        cartItems = itemsToBuy,
                        total = total,
                        voucherBytes = voucherBytes
                    )
                }
            )
        }
    }
}