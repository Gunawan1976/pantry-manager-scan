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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ToggleFloatingActionButtonDefaults.iconColor
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import org.example.pantry_manager_scan.ui.components.PantryItemCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: PantryState,
    onNavigateToAdd: () -> Unit, // Callback untuk tombol navigasi
    onDelete: (PantryItem) -> Unit
) {
// Scaffold di Compose 100% mirip dengan Scaffold di Flutter
    Scaffold(
        containerColor = Color.LightGray,
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Hallo,",
                        color = Color.Black,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Selamat Pagi",
                        color = Color.Black,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }


                Icon(
                    imageVector = Icons.Filled.AccountCircle, // Vector icon bawaan Material
                    contentDescription = "Icon Lingkaran",
                    modifier = Modifier
                        .size(75.dp) // Berfungsi sama seperti android:layout_width & height
                        .clickable { // Berfungsi sama seperti setOnClickListener
                            // Generate warna acak saat icon di-klik

                        }
                )
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.weight(1f)
            ) {
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(15.dp)
                ){
                    Card {
                        Text(
                            text = "Pantry",
                            color = Color.Black,
                            fontSize = 25.sp,
                        )
                    }
                    Card {
                        Text(
                            text = "Pantry",
                            color = Color.Black,
                            fontSize = 25.sp,
                        )
                    }
                }
//                when {
//                    state.isLoading -> {
//                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//                    }
//                    state.error != null -> {
//                        Text(
//                            text = "Error: ${state.error}",
//                            color = MaterialTheme.colorScheme.error,
//                            modifier = Modifier.align(Alignment.CenterHorizontally)
//                        )
//                    }
//                    state.items.isEmpty() -> {
//                        Text(
//                            text = "Pantry masih kosong. Yuk tambah barang!",
//                            modifier = Modifier.align(Alignment.CenterHorizontally)
//                        )
//                    }
//                    else -> {
//                        // LazyColumn = ListView.builder
//                        LazyColumn(
//                            modifier = Modifier.fillMaxSize(),
//                            contentPadding = PaddingValues(16.dp),
//                            verticalArrangement = Arrangement.spacedBy(8.dp) // Jarak antar item
//                        ) {
//                            // items() ini secara otomatis melakukan looping (seperti itemCount di Flutter)
//                            items(state.items) { item ->
//                                PantryItemCard(item = item, onDelete = { onDelete(item) })
//                            }
//                        }
//                    }
//                }
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