package com.ecommerce.app.presentation.features.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ecommerce.app.domain.model.Product
import com.ecommerce.app.presentation.components.CategoryFilter
import com.ecommerce.app.presentation.components.LandscapeProductCard
import com.ecommerce.app.presentation.components.ProductCard
import com.ecommerce.app.presentation.components.SearchInput
import com.ecommerce.app.presentation.components.getKitImagePainter
import com.ecommerce.app.presentation.components.getProductImagePainter
import com.ecommerce.app.presentation.theme.LuxuryPalette
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch

/**
 * ============================================================================
 * ✏️ LUXURY CATALOG & PRODUCT CURATION (MANGO EDITORIAL STYLING)
 * ============================================================================
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductsScreen(
    onBackClick: () -> Unit,
    viewModel: AdminViewModel
) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isSearchFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val filteredProducts by viewModel.filteredProducts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val kitsProducts = filteredProducts.filter { it.category.equals("Kits", ignoreCase = true) }
    val verticalProducts = filteredProducts.filter { !it.category.equals("Kits", ignoreCase = true) }

    Scaffold(
        containerColor = LuxuryPalette.PureWhite,
        modifier = Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { focusManager.clearFocus() },
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().background(LuxuryPalette.PureWhite)) {
                CenterAlignedTopAppBar(
                    title = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "CATALOG CURATION",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 2.sp,
                                color = LuxuryPalette.Onyx
                            )
                            Text(
                                text = "BOTANICAL FORMULATIONS",
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
                                contentDescription = "Close",
                                tint = LuxuryPalette.Onyx
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = LuxuryPalette.PureWhite)
                )
                HorizontalDivider(color = LuxuryPalette.BorderSubtle, thickness = 1.dp)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    selectedProduct = null
                    showSheet = true
                },
                containerColor = LuxuryPalette.Onyx,
                contentColor = LuxuryPalette.PureWhite,
                shape = CircleShape,
                modifier = Modifier.size(54.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Product",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(LuxuryPalette.PureWhite)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            SearchInput(
                query = searchQuery,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                isFocused = isSearchFocused,
                onFocusChange = { isSearchFocused = it },
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            CategoryFilter(
                selectedCategory = selectedCategory,
                onCategorySelected = { viewModel.updateCategory(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Section 1: Standard Products
            if (verticalProducts.isNotEmpty()) {
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
                                text = "ACTIVE EXTRACTIONS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.6.sp,
                                color = LuxuryPalette.ForestOlive
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "INDIVIDUAL FORMULATIONS",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.2.sp,
                                color = LuxuryPalette.Onyx
                            )
                        }
                        Text(
                            text = "${verticalProducts.size} ITEMS",
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
                        items(verticalProducts, key = { it.id }) { product ->
                            ProductCard(
                                product = product,
                                showCartIcon = false,
                                isAdminView = true,
                                modifier = Modifier
                                    .width(200.dp)
                                    .clickable {
                                        selectedProduct = product
                                        showSheet = true
                                    }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))
                }
            }

            // Section 2: Kits
            if (kitsProducts.isNotEmpty()) {
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
                                text = "SPECIAL COLLECTIONS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.6.sp,
                                color = LuxuryPalette.Champagne
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "CURATED SETS & RITUALS",
                                fontSize = 16.sp,
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
                                showCartIcon = false,
                                isAdminView = true,
                                modifier = Modifier.clickable {
                                    selectedProduct = product
                                    showSheet = true
                                }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                containerColor = LuxuryPalette.PureWhite,
                dragHandle = { BottomSheetDefaults.DragHandle(color = LuxuryPalette.BorderLight) }
            ) {
                EditProductSheetContent(
                    product = selectedProduct,
                    viewModel = viewModel,
                    onCloseSheet = { showSheet = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductSheetContent(
    product: Product?,
    viewModel: AdminViewModel,
    onCloseSheet: () -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var priceInput by remember { mutableStateOf(if (product != null && product.price > 0.0) product.price.toString() else "") }
    var category by remember { mutableStateOf(product?.category?.takeIf { it.isNotEmpty() } ?: "Treatments") }
    var description by remember { mutableStateOf(product?.description ?: "") }
    val allowedCategories = listOf("Kits", "Treatments", "Beauty", "Care", "Skin", "Other")
    var expandedDropdown by remember { mutableStateOf(false) }
    var selectedImageBytes by remember { mutableStateOf<ByteArray?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberFilePickerLauncher(
        type = PickerType.Image,
        mode = PickerMode.Single,
        title = "Select formulation image"
    ) { platformFile ->
        if (platformFile != null) {
            coroutineScope.launch {
                selectedImageBytes = platformFile.readBytes()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 48.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (product?.id.isNullOrEmpty()) "NEW FORMULATION CURATION" else "UPDATE FORMULATION",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp,
            color = LuxuryPalette.Onyx
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Photo Upload Frame
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LuxuryPalette.Alabaster)
                .border(1.dp, LuxuryPalette.BorderLight, RoundedCornerShape(8.dp))
                .clickable { launcher.launch() },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageBytes != null) {
                AsyncImage(
                    model = selectedImageBytes,
                    contentDescription = "New photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
                )
            } else if (!product?.imageUrl.isNullOrEmpty()) {
                val isKit = category.equals("Kits", ignoreCase = true)
                Image(
                    painter = if (isKit) getKitImagePainter(product!!.imageUrl) else getProductImagePainter(product!!.imageUrl),
                    contentDescription = "Current photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.PhotoCamera,
                        contentDescription = null,
                        tint = LuxuryPalette.TextCaption,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "ADD PHOTO",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        color = LuxuryPalette.TextCaption
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Product Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Formulation Name", fontSize = 12.sp) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LuxuryPalette.Alabaster,
                unfocusedContainerColor = LuxuryPalette.Alabaster,
                focusedBorderColor = LuxuryPalette.Onyx,
                unfocusedBorderColor = LuxuryPalette.BorderLight,
                cursorColor = LuxuryPalette.Onyx
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Price + Category Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = priceInput,
                onValueChange = { priceInput = it },
                label = { Text("Price ($)", fontSize = 12.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(6.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = LuxuryPalette.Alabaster,
                    unfocusedContainerColor = LuxuryPalette.Alabaster,
                    focusedBorderColor = LuxuryPalette.Onyx,
                    unfocusedBorderColor = LuxuryPalette.BorderLight,
                    cursorColor = LuxuryPalette.Onyx
                )
            )

            // Category Selector
            ExposedDropdownMenuBox(
                expanded = expandedDropdown,
                onExpandedChange = { expandedDropdown = !expandedDropdown },
                modifier = Modifier.weight(1f)
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category", fontSize = 12.sp) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                    modifier = Modifier.menuAnchor(),
                    shape = RoundedCornerShape(6.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = LuxuryPalette.Alabaster,
                        unfocusedContainerColor = LuxuryPalette.Alabaster,
                        focusedBorderColor = LuxuryPalette.Onyx,
                        unfocusedBorderColor = LuxuryPalette.BorderLight
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedDropdown,
                    onDismissRequest = { expandedDropdown = false },
                    modifier = Modifier.background(LuxuryPalette.PureWhite)
                ) {
                    allowedCategories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption, fontSize = 13.sp, color = LuxuryPalette.Onyx) },
                            onClick = {
                                category = selectionOption
                                expandedDropdown = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Description Field
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Botanical Description", fontSize = 12.sp) },
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp),
            shape = RoundedCornerShape(6.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = LuxuryPalette.Alabaster,
                unfocusedContainerColor = LuxuryPalette.Alabaster,
                focusedBorderColor = LuxuryPalette.Onyx,
                unfocusedBorderColor = LuxuryPalette.BorderLight,
                cursorColor = LuxuryPalette.Onyx
            )
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Save Button
        Button(
            onClick = {
                val productToSave = Product(
                    id = product?.id ?: "",
                    name = name,
                    price = priceInput.toDoubleOrNull() ?: 0.0,
                    category = category,
                    description = description,
                    imageUrl = product?.imageUrl ?: ""
                )
                viewModel.saveProduct(productToSave, selectedImageBytes)
                onCloseSheet()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LuxuryPalette.Onyx)
        ) {
            Text(
                text = "SAVE FORMULATION",
                color = LuxuryPalette.PureWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.4.sp
            )
        }

        if (!product?.id.isNullOrEmpty()) {
            TextButton(
                onClick = {
                    viewModel.deleteProduct(product!!.id)
                    onCloseSheet()
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = "ARCHIVE THIS PRODUCT",
                    color = LuxuryPalette.Terracotta,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}