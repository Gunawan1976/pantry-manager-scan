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
}