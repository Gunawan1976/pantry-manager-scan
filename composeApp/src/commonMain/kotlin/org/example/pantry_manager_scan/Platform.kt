package org.example.pantry_manager_scan

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform