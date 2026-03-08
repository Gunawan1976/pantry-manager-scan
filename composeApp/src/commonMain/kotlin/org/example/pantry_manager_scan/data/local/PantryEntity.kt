package org.example.pantry_manager_scan.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pantry_items")
data class PantryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // ID akan otomatis bertambah (Auto Increment)
    val name: String,
    val category: String,
    val expiryDateMillis: Long,
    val isConsumed: Boolean
)