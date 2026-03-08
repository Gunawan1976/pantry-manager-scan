package org.example.pantry_manager_scan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

// Daftarkan semua Entity (tabel) di sini. Jika ada perubahan struktur, naikkan version-nya.
@Database(entities = [PantryEntity::class], version = 1)
abstract class PantryDatabase : RoomDatabase() {
    // Daftarkan DAO di sini
    abstract fun pantryDao(): PantryDao
}