package org.example.pantry_manager_scan.ui.viewmodel

import org.example.pantry_manager_scan.domain.model.PantryItem

data class PantryState(
    val items:List<PantryItem> = emptyList(),
    val isLoading: Boolean = false,
    val error:String? = null
)

sealed interface PantryEvent{
    object LoadItems : PantryEvent
    data class DeleteItem(val item: PantryItem): PantryEvent
    data class SaveItem(val name: String, val category: String, val expiryDateMillis: Long,val isConsumed : Boolean) : PantryEvent
}
