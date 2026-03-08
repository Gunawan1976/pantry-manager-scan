package org.example.pantry_manager_scan.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.ui.viewmodel.PantryState
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.iconColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: PantryState,
    onNavigateToAdd: () -> Unit, // Callback untuk tombol navigasi
    onDelete: (PantryItem) -> Unit
) {
// Scaffold di Compose 100% mirip dengan Scaffold di Flutter
    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Pantry test 2") },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            )
//        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                // Mengatur posisi horizontal agar kumpul di tengah
                horizontalArrangement = Arrangement.SpaceBetween,
                // Mengatur posisi vertikal agar sejajar di tengah
                verticalAlignment = Alignment.CenterVertically,
                // Sama seperti android:layout_width="match_parent" di XML
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text(
                    text = "Hallo, \nSelamat Pagi",
                    color = Color.Black,
                    fontSize = 25.sp,
                )

                Icon(
                    imageVector = Icons.Filled.AccountCircle, // Vector icon bawaan Material
                    contentDescription = "Icon Lingkaran",
                    modifier = Modifier
                        .size(55.dp) // Berfungsi sama seperti android:layout_width & height
                        .clickable { // Berfungsi sama seperti setOnClickListener
                            // Generate warna acak saat icon di-klik

                        }
                )
            }


            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                state.items.isEmpty() -> {
                    Text(
                        text = "Pantry masih kosong. Yuk tambah barang!",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {
                    // LazyColumn = ListView.builder
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar item
                    ) {
                        // items() ini secara otomatis melakukan looping (seperti itemCount di Flutter)
                        items(state.items) { item ->
                            PantryItemCard(item = item, onDelete = { onDelete(item) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PantryItemCard(item: PantryItem, onDelete: () -> Unit) {
    val currentTime = Clock.System.now().toEpochMilliseconds()
    val daysRemaining = item.getDaysRemaining(currentTime)

    // Logika warna sederhana berdasarkan sisa hari kedaluwarsa
    val containerColor = when {
        daysRemaining < 0 -> MaterialTheme.colorScheme.errorContainer
        daysRemaining <= 3 -> MaterialTheme.colorScheme.tertiaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(text = item.category, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (daysRemaining < 0) "Sudah Kedaluwarsa!" else "Sisa: $daysRemaining hari",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (daysRemaining < 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Tombol Hapus (Icon Button)
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Hapus",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen(
            state = PantryState(
                items = listOf(
                    PantryItem(id = 1, name = "Susu UHT", category = "Minuman", expiryDateMillis = 1735689600000L, isConsumed = false),
                    PantryItem(id = 2, name = "Roti Tawar", category = "Makanan", expiryDateMillis = 1735689600000L, isConsumed = false)
                ),
                isLoading = false
            ),
            onNavigateToAdd = {},
            onDelete = {}
        )
    }
}