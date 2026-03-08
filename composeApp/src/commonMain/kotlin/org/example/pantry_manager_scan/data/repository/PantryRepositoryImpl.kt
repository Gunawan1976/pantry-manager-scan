package org.example.pantry_manager_scan.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.pantry_manager_scan.data.local.PantryDao
import org.example.pantry_manager_scan.data.local.PantryEntity
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.domain.repository.PantryRepository

class PantryRepositoryImpl(
    private val dao: PantryDao // Menerima DAO dari Room
) : PantryRepository {
    override fun getAllItem(): Flow<List<PantryItem>> {
        // Ambil data dari database (berupa Flow<List<PantryEntity>>)
        return dao.getAllItems().map { entityList ->
            // Terjemahkan/Map setiap Entity menjadi Domain Model
            entityList.map { entity ->
                PantryItem(
                    id = entity.id,
                    name = entity.name,
                    category = entity.category,
                    expiryDateMillis = entity.expiryDateMillis,
                    isConsumed = entity.isConsumed
                )
            }
        }
    }

    override suspend fun insertItem(item: PantryItem) {
        // Terjemahkan Domain Model menjadi Entity sebelum disimpan ke DB
        val entity = PantryEntity(
            id = item.id,
            name = item.name,
            category = item.category,
            expiryDateMillis = item.expiryDateMillis,
            isConsumed = item.isConsumed ?: false
        )
        dao.insertItem(entity)
    }

    override suspend fun deleteItem(item: PantryItem) {
        val entity = PantryEntity(
            id = item.id,
            name = item.name,
            category = item.category,
            expiryDateMillis = item.expiryDateMillis,
            isConsumed = item.isConsumed  ?: false
        )
        dao.deleteItem(entity)
    }


}