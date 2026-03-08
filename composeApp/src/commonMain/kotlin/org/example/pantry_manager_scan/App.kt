package org.example.pantry_manager_scan

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.ui.screen.HomeScreen
import org.example.pantry_manager_scan.ui.viewmodel.PantryState
import org.jetbrains.compose.resources.painterResource

import pantrymanagerscan.composeapp.generated.resources.Res
import pantrymanagerscan.composeapp.generated.resources.compose_multiplatform
import kotlinx.datetime.Clock


@Composable
@Preview
fun App() {
    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.primaryContainer)
//                .safeContentPadding()
//                .fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
        val now = Clock.System.now().toEpochMilliseconds()
        val oneDay = 86400000L

        val dummyData = listOf(
            PantryItem(id = 1, name="Susu UHT", category = "Minuman", expiryDateMillis = now + (5 * oneDay), isConsumed =false ), // Sisa 5 hari
            PantryItem(id = 2, name = "Roti Tawar", category ="Makanan", expiryDateMillis =now + (2 * oneDay), isConsumed =false ), // Sisa 2 hari
            PantryItem(3, name = "Saus Tomat", category ="Bumbu", expiryDateMillis =now - (1 * oneDay), isConsumed =false )  // Expired 1 hari
        )

        // 2. Bungkus ke dalam State
        val dummyState = PantryState(
            items = dummyData,
            isLoading = false
        )

        // 3. Tampilkan UI-nya
        HomeScreen(
            state = dummyState,
            onNavigateToAdd = { println("Navigasi ke halaman tambah diklik!") },
            onDelete = { item -> println("Barang ${item.name} dihapus!") }
        )
    }
}