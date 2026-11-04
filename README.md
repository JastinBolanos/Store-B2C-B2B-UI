<div align="center">
  <img src="docs/logo.remedioznatura.png" alt="Remedioz Natura Logo" width="100" />

  <h1>Store-UI</h1>
  <p><strong>Android Retail Client Architecture (B2C & B2B)</strong></p>

[![Kotlin](https://img.shields.io/badge/Kotlin-2.x-blue.svg?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack-Compose-green.svg?style=flat-square&logo=android)](https://developer.android.com/jetpack/compose)
[![Android](https://img.shields.io/badge/Platform-Android%20Native-success.svg?style=flat-square&logo=android)]()
[![Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20UDF-orange.svg?style=flat-square)]()

<p align="center">
  <a href="#">
    <img src="https://img.shields.io/badge/App-Android%20Native-success?style=for-the-badge&logo=android&logoColor=white" alt="Android App">
  </a>
</p>
</div>

---

## Overview

**Store-UI** is an open architecture showcase demonstrating how consumer shopping experiences (B2C) and administrative interfaces (B2B) can coexist in a single, unified Android codebase using **Kotlin** and **Jetpack Compose**.

---

## App Preview

### 🛒 B2C: Consumer Ecosystem
> Localized retail experience with dynamic carts and QR payment workflows.

| Catalog & Discovery | Cart Management | QR Checkout |
| :---: | :---: | :---: |
| <img src="docs/01_home_catalog.png" width="220" alt="Catalog"/> | <img src="docs/04_shopping_cart.png" width="220" alt="Cart"/> | <img src="docs/05_payment_checkout.png" width="220" alt="Checkout"/> |

### 🛠️ B2B: Backoffice & Admin Mode
> Client-side management interface for order auditing and inventory control.

| Admin Dashboard | Pending Orders | Inventory Management |
| :---: | :---: | :---: |
| <img src="docs/12_admin_dashboard_menu.png" width="220" alt="Dashboard"/> | <img src="docs/07_admin_pending_orders.png" width="220" alt="Orders"/> | <img src="docs/13_admin_product_management.png" width="220" alt="Inventory"/> |

---

## Technical Foundations

* **Core & UI:** Native Android with Jetpack Compose and Material 3 design system.
* **Architecture:** Clean Architecture + Unidirectional Data Flow (UDF). Strict separation between `domain`, `data`, and `presentation` layers.
* **State Management:** Reactive states driven by Kotlin `StateFlow` and Coroutines.
* **Infrastructure:** Gradle Version Catalogs (`libs.versions.toml`) and Android Gradle Plugin (AGP).

---

## Routing & Logic Map

| User Role | Main Navigation Flow |
| :--- | :--- |
| **🛍️ Customer (B2C)** | Catalog ➔ Shopping Cart ➔ QR Checkout ➔ Order Tracking |
| **🛡️ Admin (B2B)** | Dashboard ➔ Pending Orders ➔ Payment Audit ➔ Shipping |
| **📦 Admin (CMS)** | Dashboard ➔ Inventory & Product Management |

---

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/JastinBolanos/Store-B2C-B2B-UI
   cd Store-B2C-B2B-UI
   ```

2. Open the project in **Android Studio** (Ladybug or newer).
3. Build the debug APK:
   ```bash
   ./gradlew assembleDebug
   ```
