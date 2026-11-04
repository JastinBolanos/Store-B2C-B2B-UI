package com.ecommerce.app

import android.os.Build

/**
 * ============================================================================
 * ANDROID PLATFORM IMPLEMENTATION
 * ============================================================================
 */
interface Platform {
    val name: String
}

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

fun getPlatform(): Platform = AndroidPlatform()