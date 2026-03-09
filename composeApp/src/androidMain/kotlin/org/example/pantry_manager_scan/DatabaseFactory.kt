package org.example.pantry_manager_scan

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.example.pantry_manager_scan.data.local.PantryDatabase

// 'actual' berarti ini adalah wujud nyata dari 'expect' di commonMain.
// Di Android, kita butuh 'Context' untuk membuat file database.
actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<PantryDatabase> {
        // Meminta Android untuk mencarikan lokasi penyimpanan internal yang aman
        val dbFile = context.getDatabasePath("pantry_database.db")

        return Room.databaseBuilder<PantryDatabase>(
            context = context.applicationContext,
            name = dbFile.absolutePath
        )
    }
}