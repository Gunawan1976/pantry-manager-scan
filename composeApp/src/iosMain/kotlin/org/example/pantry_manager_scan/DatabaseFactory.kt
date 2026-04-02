package org.example.pantry_manager_scan

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.example.pantry_manager_scan.data.local.PantryDatabase
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    @OptIn(ExperimentalForeignApi::class)
    actual fun create(): RoomDatabase.Builder<PantryDatabase> {
        // Memanggil API Apple (Foundation) menggunakan Kotlin!
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )

        // Gabungkan path direktori iOS dengan nama file database kita
        val path = requireNotNull(documentDirectory?.path) + "/pantry_database.db"

        return Room.databaseBuilder<PantryDatabase>(
            name = path,
            factory = { PantryDatabase::class.instantiateImpl() } // Wajib untuk Room iOS
        )
    }
}