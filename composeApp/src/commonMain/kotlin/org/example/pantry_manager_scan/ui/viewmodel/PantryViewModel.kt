package org.example.pantry_manager_scan.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.domain.repository.PantryRepository

class PantryViewModel(private val repository: PantryRepository) : ViewModel() {
    private val _state = MutableStateFlow(PantryState())

    val state: StateFlow<PantryState> = _state.asStateFlow()

    init {
        onEvent(PantryEvent.LoadItems)
    }

    fun onEvent(event: PantryEvent) {
        when (event) {
            is PantryEvent.LoadItems -> loadItems()
            is PantryEvent.DeleteItem -> deleteItem(event.item)
            is PantryEvent.SaveItem -> saveItem(event.name, event.category, event.expiryDateMillis,event.isConsumed,event.quantity,event.barcode ?: "")
        }
    }

    private fun loadItems() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            try {
                repository.getAllItem().collect { value ->
                    _state.update { it.copy(items = value, isLoading = false, error = null) }
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message ?: "Terjadi Kesalahan tidak dikenal") }
            }
        }
    }

    private fun deleteItem(item: PantryItem) {
        viewModelScope.launch {
            repository.deleteItem(item = item)
        }
    }

    suspend fun checkLocalCache(barcode: String): String? {
        return repository.getProductNameByBarcode(barcode)
    }

    private fun saveItem(
        name: String,
        category: String,
        expiryDateMillis: Long,
        isConsumed: Boolean,
        quantity: Int,
        barcode: String,
    ) {
        viewModelScope.launch {
            val newItem = PantryItem(
                name = name,
                category = category,
                expiryDateMillis = expiryDateMillis,
                isConsumed = isConsumed,
                quantity = quantity,
                barcode = barcode
            )
            repository.insertItem(newItem) // Kirim ke Room Database!

            // Karena kita pakai Flow di getAllItems(),
            // UI HomeScreen akan otomatis ter-update tanpa perlu dipanggil ulang!
        }
    }
}