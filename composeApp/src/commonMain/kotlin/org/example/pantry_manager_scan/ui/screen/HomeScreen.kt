package org.example.pantry_manager_scan.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.ui.components.PantryItemCard
import org.example.pantry_manager_scan.ui.viewmodel.PantryState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: PantryState,
    onNavigateToAdd: () -> Unit,
    onDelete: (PantryItem) -> Unit
) {
    val scrollState = rememberScrollState()

    val currentTime = Clock.System.now().toEpochMilliseconds()

    val totalBarang = state.items.size

    val akanKedaluwarsa = state.items.count { item ->
        val sisaHari = item.getDaysRemaining(currentTime)
        sisaHari in 0..7
    }

    Scaffold(
        containerColor = Color.LightGray,
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToAdd) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(12.dp)
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
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Account Icon",
                    modifier = Modifier
                        .size(75.dp)
                        .clickable { }
                )
            }

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
//                modifier = Modifier.weight(1f).fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFF8C00).copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.weight(1f).aspectRatio(1f)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize().padding(16.dp)
                            ) {
                                Text(
                                    text = "Akan Kadaluwarsa\n(7 Hari)",
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "$akanKedaluwarsa",
                                    color = Color(0xFFFF8C00),
                                    fontSize = 55.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFD400).copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.weight(1f).aspectRatio(1f)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize().padding(16.dp)
                            ) {
                                Text(
                                    text = "Total Barang\ndi Dapur",
                                    color = Color.Black,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "$totalBarang",
                                    color = Color(0xFFFFD400),
                                    fontSize = 55.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Gunakan Sekarang")

                    Spacer(modifier = Modifier.height(16.dp))

                    LazyRow {
                        items(state.items) { item ->
                            ElevatedCard(
                                modifier = Modifier.padding(horizontal = 8.dp)
                                    .size(120.dp)
                                    .border(border = BorderStroke(0.4.dp, Color.Black), shape =RoundedCornerShape(corner = CornerSize(size = 12.dp))),
                                colors = CardDefaults.cardColors(Color.White)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = "Account roti",
                                        modifier = Modifier
                                            .size(75.dp)
                                            .clickable { }
                                    )

                                    Text(
                                        text = "telur"
                                    )
                                }
                            }
                        }
                    }
                }

//                when {
//                    state.isLoading -> {
//                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                            CircularProgressIndicator()
//                        }
//                    }
//                    state.error != null -> {
//                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                            Text(
//                                text = "Error: ${state.error}",
//                                color = MaterialTheme.colorScheme.error
//                            )
//                        }
//                    }
//                    state.items.isEmpty() -> {
//                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                            Text(
//                                text = "Pantry masih kosong. Yuk tambah barang!",
//                                style = MaterialTheme.typography.bodyLarge
//                            )
//                        }
//                    }
//                    else -> {
//                        Spacer(modifier = Modifier.height(24.dp))
//
//                        state.items.forEach { item->
//                            Box (
//                                modifier = Modifier.padding(top = 8.dp, start = 12.dp, end = 12.dp)
//                            ){
//                                PantryItemCard(item = item, onDelete = { onDelete(item) })
//                            }
//                            Spacer(modifier = Modifier.height(8.dp))
//                        }
//
//                        Spacer(modifier = Modifier.height(100.dp)) // Ruang untuk Bottom Nav
////                        LazyColumn(
////                            modifier = Modifier.fillMaxSize(),
////                            contentPadding = PaddingValues(16.dp),
////                            verticalArrangement = Arrangement.spacedBy(8.dp)
////                        ) {
////                            items(state.items) { item ->
////                                PantryItemCard(item = item, onDelete = { onDelete(item) })
////                            }
////                        }
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
            onDelete = {}
        )
    }
}
