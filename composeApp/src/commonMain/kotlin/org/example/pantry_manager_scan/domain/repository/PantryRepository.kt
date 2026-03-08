
package org.example.pantry_manager_scan.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.pantry_manager_scan.domain.model.PantryItem

interface PantryRepository {
    fun getAllItem(): Flow<List<PantryItem>>

    suspend fun insertItem(item: PantryItem)

    suspend fun deleteItem(item: PantryItem)
}