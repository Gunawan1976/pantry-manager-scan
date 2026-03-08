package org.example.pantry_manager_scan.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PantryDao {
    // Membaca semua data. Menggunakan Flow agar UI selalu dapat data terbaru
    @Query("SELECT * FROM pantry_items ORDER BY expiryDateMillis ASC")
    fun getAllItems(): Flow<List<PantryEntity>>

    // Menyimpan atau mengupdate data (jika ID sudah ada, data direplace)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: PantryEntity)

    // Menghapus data
    @Delete
    suspend fun deleteItem(item: PantryEntity)
}