package org.example.pantry_manager_scan.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.ui.components.PantryItemCard
import org.example.pantry_manager_scan.ui.viewmodel.PantryState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    state: PantryState,
    onNavigateToAdd: () -> Unit,
    onDelete: (PantryItem) -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        // Perbaikan nilai alpha menjadi 0.1f
        containerColor = Color.LightGray.copy(alpha = 0.1f),
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        // 1. Ganti Column utama menjadi Box agar state loading/error/empty bisa di tengah
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
            // verticalScroll DIHAPUS DARI SINI
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.items.isEmpty() -> {
                    // 2. Sekarang teks ini pasti presisi di tengah layar Box!
                    Text(
                        text = "Pantry masih kosong. Yuk tambah barang!",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    // 3. Pindahkan verticalScroll HANYA ke bagian list item ini
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))

                        state.items.forEach { item ->
                            Box(
                                modifier = Modifier.padding(top = 8.dp, start = 12.dp, end = 12.dp)
                            ) {
                                PantryItemCard(item = item, onDelete = { onDelete(item) })
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        Spacer(modifier = Modifier.height(100.dp)) // Ruang untuk Bottom Nav
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun InventoryScreenPreview() {
    MaterialTheme {
        InventoryScreen(
            state = PantryState(
                items = listOf(
                    PantryItem(
                        id = 1,
                        name = "Susu UHT",
                        category = "Minuman",
                        expiryDateMillis = 1735689600000L,
                        isConsumed = false
                    ),
                    PantryItem(
                        id = 2,
                        name = "Roti Tawar",
                        category = "Makanan",
                        expiryDateMillis = 1735689600000L,
                        isConsumed = false
                    )
                ),
                isLoading = false
            ),
            onNavigateToAdd = {},
            onDelete = {},
        )
    }
}