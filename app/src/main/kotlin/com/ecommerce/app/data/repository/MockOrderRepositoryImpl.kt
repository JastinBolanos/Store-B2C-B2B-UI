package com.ecommerce.app.data.repository

import com.ecommerce.app.data.platform.getCurrentTimeMillis
import com.ecommerce.app.domain.model.CartItem
import com.ecommerce.app.domain.model.Order
import com.ecommerce.app.domain.model.Product
import com.ecommerce.app.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ============================================================================
 * 🗄️ ENRICHED MOCK ORDER REPOSITORY (ATELIER IN-MEMORY STORE)
 * ============================================================================
 */
class MockOrderRepositoryImpl : OrderRepository {

    // --- 1. LUXURY BOTANICAL PRODUCTS & KITS ---

    private val productPinkPeptide = Product(
        id = "1",
        name = "Pink Peptide Illuminating Serum",
        price = 85.0,
        category = "Skin",
        description = "Concentrated active peptides that provide crystal radiance and instant cellular firmness.",
        imageUrl = "img_serum_pink_peptide"
    )

    private val productCremaDolkong = Product(
        id = "2",
        name = "Dolkong Nourishing Facial Cream",
        price = 68.0,
        category = "Skin",
        description = "Rich texture enriched with cold-pressed pure soy extracts for deep barrier restoration.",
        imageUrl = "img_crema_dolkong"
    )

    private val productTonico = Product(
        id = "3",
        name = "Rose Revitalizing Serum",
        price = 75.0,
        category = "Skin",
        description = "Concentrated formula with pure Damask rose extracts for luminous, balanced skin.",
        imageUrl = "img_tonico_purificante"
    )

    private val productCremaNoche = Product(
        id = "4",
        name = "Cellular Night Recovery Elixir",
        price = 95.0,
        category = "Skin",
        description = "Intensive evening botanical restorative treatment to awaken with refreshed elasticity.",
        imageUrl = "img_crema_noche"
    )

    private val productJabonManzanilla = Product(
        id = "5",
        name = "Chamomile Gentle Cleansing Bar",
        price = 25.0,
        category = "Skin",
        description = "Solid cold-processed soap to soothe sensitive skin and diminish surface redness.",
        imageUrl = "img_jabon_manzanilla"
    )

    private val productPerfumeRojo = Product(
        id = "8",
        name = "Ruby Saffron Botanical Perfume",
        price = 145.0,
        category = "Beauty",
        description = "Artisanal extrait de parfum featuring crushed red berries, warm saffron, and amber cedar.",
        imageUrl = "img_perfume_rojo"
    )

    private val productPerfumeAzul = Product(
        id = "10",
        name = "Ocean Breeze Eau de Parfum",
        price = 130.0,
        category = "Beauty",
        description = "Aquatic botanical essences evoking crisp morning mist, crushed salt crystals, and water lily.",
        imageUrl = "img_perfume_azul"
    )

    private val productArgan = Product(
        id = "13",
        name = "Pure Golden Argan Elixir",
        price = 58.0,
        category = "Care",
        description = "Cold-pressed Moroccan argan oil for radiant hair shine and intense dermal nourishment.",
        imageUrl = "img_aceite_dorado"
    )

    private val productShampooSolido = Product(
        id = "16",
        name = "Nettle & Rosemary Solid Shampoo",
        price = 32.0,
        category = "Care",
        description = "Zero-waste botanical hair wash infused with essential rosemary for scalp stimulation.",
        imageUrl = "img_shampoo_solido"
    )

    private val productEsenciaLechosa = Product(
        id = "19",
        name = "Milky Rice Ceramide Essence",
        price = 92.0,
        category = "Treatments",
        description = "Micro-filtered rice ferment emulsion designed to lock deep moisture into thirsty skin cells.",
        imageUrl = "img_esencia_lechosa"
    )

    private val productKitCitrico = Product(
        id = "25",
        name = "Citrus Energy Gift Kit",
        price = 155.0,
        category = "Kits",
        description = "Revitalizing citrus ritual with perfume, hand cream, and botanical body wash in a keepsake box.",
        imageUrl = "img_kit_citrico"
    )

    private val productKitBotanico = Product(
        id = "26",
        name = "Premium Botanical Spa Kit",
        price = 270.0,
        category = "Kits",
        description = "The ultimate atelier spa ritual with essential oils, bath crystals, and velvet body cream.",
        imageUrl = "img_kit_botanico"
    )

    private val productKitMinimalista = Product(
        id = "27",
        name = "Minimalist Daily Routine Kit",
        price = 140.0,
        category = "Kits",
        description = "Essential morning and evening routine with pure balancing formulas in minimalist glass flacons.",
        imageUrl = "img_kit_minimalista"
    )

    private val productKitCentella = Product(
        id = "28",
        name = "Centella Asiatica Barrier Kit",
        price = 180.0,
        category = "Kits",
        description = "Calming skincare collection enriched with tiger grass for barrier defense and recovery.",
        imageUrl = "img_kit_centella"
    )

    // --- 2. EXTENSIVE REALISTIC DATASET (18 ORDERS ACROSS ALL ATELIER STAGES) ---

    private val now = getCurrentTimeMillis()

    private val mockOrders = mutableListOf(
        // ==================== PENDING ORDERS (ATELIER ALERTS QUEUE) ====================
        // Alert 1: Recent client payment (5 min ago)
        Order(
            id = "ORD-99882",
            userId = "USER-VALENTINA",
            customerName = "Valentina Thorne",
            items = listOf(CartItem(productKitBotanico, 1)),
            totalAmount = 270.0,
            voucherUrl = "voucher_bcp_1",
            status = "Pending",
            timestamp = now - (5 * 60 * 1000L)
        ),
        // Alert 2: Client order awaiting voucher verification (18 min ago)
        Order(
            id = "ORD-99814",
            userId = "USER-ELENA",
            customerName = "Elena de Alvear",
            items = listOf(CartItem(productPinkPeptide, 2), CartItem(productTonico, 1)),
            totalAmount = 245.0,
            voucherUrl = "voucher_yape_1",
            status = "Pending",
            timestamp = now - (18 * 60 * 1000L)
        ),
        // Alert 3: Luxury kit purchase (32 min ago)
        Order(
            id = "ORD-99750",
            userId = "USER-SEBASTIAN",
            customerName = "Sebastian Rousseau",
            items = listOf(CartItem(productKitCentella, 1), CartItem(productArgan, 1)),
            totalAmount = 238.0,
            voucherUrl = "voucher_bcp_2",
            status = "Pending",
            timestamp = now - (32 * 60 * 1000L)
        ),
        // Alert 4: Fragrance acquisition (48 min ago)
        Order(
            id = "ORD-99691",
            userId = "USER-CAMILA",
            customerName = "Camila Montes",
            items = listOf(CartItem(productPerfumeRojo, 1)),
            totalAmount = 145.0,
            voucherUrl = "voucher_plin_1",
            status = "Pending",
            timestamp = now - (48 * 60 * 1000L)
        ),
        // Alert 5: Laura Veracruz recent order (1 hour ago - appears in profile too!)
        Order(
            id = "ORD-99620",
            userId = "USER-LAURA",
            customerName = "Laura Veracruz",
            items = listOf(CartItem(productKitMinimalista, 1), CartItem(productShampooSolido, 1)),
            totalAmount = 172.0,
            voucherUrl = "voucher_yape_2",
            status = "Pending",
            timestamp = now - (62 * 60 * 1000L)
        ),
        // Alert 6: Hair & body ritual (2 hours ago)
        Order(
            id = "ORD-99540",
            userId = "USER-MATEO",
            customerName = "Mateo Valenzuela",
            items = listOf(CartItem(productArgan, 2), CartItem(productShampooSolido, 2)),
            totalAmount = 180.0,
            voucherUrl = "voucher_bcp_3",
            status = "Pending",
            timestamp = now - (2 * 3600 * 1000L)
        ),
        // Alert 7: Evening recovery purchase (3 hours ago)
        Order(
            id = "ORD-99412",
            userId = "USER-SOFIA",
            customerName = "Sofia Lindqvist",
            items = listOf(CartItem(productCremaNoche, 1), CartItem(productEsenciaLechosa, 1)),
            totalAmount = 187.0,
            voucherUrl = "voucher_plin_2",
            status = "Pending",
            timestamp = now - (3 * 3600 * 1000L)
        ),

        // ==================== APPROVED ORDERS (PACKAGING QUEUE) ====================
        // Order 8: Approved order
        Order(
            id = "ORD-98011",
            userId = "USER-CAMILA",
            customerName = "Camila de la Fuente",
            items = listOf(CartItem(productPinkPeptide, 1)),
            totalAmount = 85.0,
            voucherUrl = "voucher_yape_3",
            status = "Approved",
            timestamp = now - (5 * 3600 * 1000L)
        ),
        // Order 9: Laura Veracruz approved gift kit (appears in profile!)
        Order(
            id = "ORD-97880",
            userId = "USER-LAURA",
            customerName = "Laura Veracruz",
            items = listOf(CartItem(productKitCitrico, 1)),
            totalAmount = 155.0,
            voucherUrl = "voucher_bcp_1",
            status = "Approved",
            timestamp = now - (8 * 3600 * 1000L)
        ),
        // Order 10: Approved cleansing order
        Order(
            id = "ORD-97210",
            userId = "USER-LUCAS",
            customerName = "Lucas Beaumont",
            items = listOf(CartItem(productJabonManzanilla, 4)),
            totalAmount = 100.0,
            voucherUrl = "voucher_plin_1",
            status = "Approved",
            timestamp = now - (14 * 3600 * 1000L)
        ),

        // ==================== PREPARING PACKAGE ORDERS ====================
        // Order 11: Being bottled in atelier
        Order(
            id = "ORD-96104",
            userId = "USER-ISABELLA",
            customerName = "Isabella Santillán",
            items = listOf(CartItem(productPerfumeAzul, 2)),
            totalAmount = 260.0,
            voucherUrl = "voucher_bcp_2",
            status = "Preparing Package",
            timestamp = now - 86400000L
        ),
        // Order 12: Laura Veracruz preparing order
        Order(
            id = "ORD-95320",
            userId = "USER-LAURA",
            customerName = "Laura Veracruz",
            items = listOf(CartItem(productEsenciaLechosa, 1), CartItem(productTonico, 1)),
            totalAmount = 167.0,
            voucherUrl = "voucher_yape_1",
            status = "Preparing Package",
            timestamp = now - (98000000L)
        ),

        // ==================== IN TRANSIT ORDERS (COURIER ACTIVE) ====================
        // Order 13: En route with climate-neutral courier
        Order(
            id = "ORD-94112",
            userId = "USER-ANDREA",
            customerName = "Andrea Castañeda",
            items = listOf(CartItem(productKitCitrico, 1)),
            totalAmount = 155.0,
            voucherUrl = "voucher_plin_2",
            status = "In Transit",
            timestamp = now - 172800000L
        ),
        // Order 14: Laura Veracruz in transit package
        Order(
            id = "ORD-93040",
            userId = "USER-LAURA",
            customerName = "Laura Veracruz",
            items = listOf(CartItem(productPinkPeptide, 1), CartItem(productCremaDolkong, 1)),
            totalAmount = 153.0,
            voucherUrl = "voucher_bcp_3",
            status = "In Transit",
            timestamp = now - (210000000L)
        ),

        // ==================== DELIVERED ORDERS (COMPLETED) ====================
        // Order 15: Laura Veracruz completed delivery (historic order)
        Order(
            id = "ORD-77382",
            userId = "USER-LAURA",
            customerName = "Laura Veracruz",
            items = listOf(CartItem(productArgan, 2)),
            totalAmount = 116.0,
            voucherUrl = "voucher_yape_2",
            status = "Delivered",
            timestamp = now - 864000000L
        ),
        // Order 16: Completed delivery
        Order(
            id = "ORD-74219",
            userId = "USER-JULIA",
            customerName = "Julia Navarro",
            items = listOf(CartItem(productPerfumeRojo, 1), CartItem(productArgan, 1)),
            totalAmount = 203.0,
            voucherUrl = "voucher_bcp_1",
            status = "Delivered",
            timestamp = now - (950000000L)
        ),
        // Order 17: Historic kit delivery
        Order(
            id = "ORD-71005",
            userId = "USER-GABRIEL",
            customerName = "Gabriel Osorio",
            items = listOf(CartItem(productKitBotanico, 1)),
            totalAmount = 270.0,
            voucherUrl = "voucher_plin_1",
            status = "Delivered",
            timestamp = now - (1200000000L)
        ),

        // ==================== REJECTED ORDERS (INVALID PROOF) ====================
        // Order 18: Sample unverified order
        Order(
            id = "ORD-68910",
            userId = "USER-UNKNOWN",
            customerName = "Diego Morales",
            items = listOf(CartItem(productCremaNoche, 1)),
            totalAmount = 95.0,
            voucherUrl = "voucher_invalid",
            status = "Rejected",
            timestamp = now - (1500000000L)
        )
    )

    private val ordersFlow = MutableStateFlow<List<Order>>(mockOrders.toList())

    override fun observeOrders(): Flow<List<Order>> {
        return ordersFlow.asStateFlow()
    }

    override suspend fun addOrder(order: Order): Boolean {
        mockOrders.add(0, order)
        ordersFlow.value = mockOrders.toList()
        return true
    }

    override suspend fun updateOrderStatus(orderId: String, newStatus: String): Boolean {
        val index = mockOrders.indexOfFirst { it.id == orderId }

        if (index != -1) {
            val updatedOrder = mockOrders[index].copy(status = newStatus)
            mockOrders[index] = updatedOrder
            ordersFlow.value = mockOrders.toList()
            return true
        }
        return false
    }
}