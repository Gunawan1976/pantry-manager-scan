package org.example.pantry_manager_scan

import androidx.room.RoomDatabase
import org.example.pantry_manager_scan.data.local.PantryDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<PantryDatabase>
}

// Fungsi bantuan untuk merakit database setelah builder-nya didapatkan dari platform
fun getRoomDatabase(factory: DatabaseFactory): PantryDatabase {
    return factory.create()
        // Konfigurasi tambahan Room untuk KMP bisa ditaruh di sini
        // .setDriver(BundledSQLiteDriver()) // (Dibutuhkan oleh Room KMP)
        .fallbackToDestructiveMigration(true)
        .build()
}