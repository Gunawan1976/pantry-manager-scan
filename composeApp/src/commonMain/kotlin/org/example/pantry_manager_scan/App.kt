package org.example.pantry_manager_scan

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.ui.screen.HomeScreen
import org.example.pantry_manager_scan.ui.viewmodel.PantryState
import kotlinx.datetime.Clock
import org.example.pantry_manager_scan.ui.screen.AddItemScreen
import org.example.pantry_manager_scan.ui.viewmodel.PantryEvent
import org.example.pantry_manager_scan.ui.viewmodel.PantryViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.compose.KoinContext

enum class ScreenRoute { Home, AddItem }

@Composable
@Preview
fun App() {
    KoinContext {
        MaterialTheme {
            // 2. Minta ViewModel dari Koin secara otomatis! (Keajaiban DI)
            val viewModel = koinViewModel<PantryViewModel>()

            // 3. Dengarkan perubahan State dari ViewModel (Mirip BlocBuilder/watch)
            val state by viewModel.state.collectAsState()

            var currentScreen by remember { mutableStateOf(ScreenRoute.Home) }

            val now = Clock.System.now().toEpochMilliseconds()
            val oneDay = 86400000L

//            val dummyData = listOf(
//                PantryItem(id = 1, name = "Susu UHT", category = "Minuman", expiryDateMillis = now + (5 * oneDay), isConsumed = false), // Sisa 5 hari
//                PantryItem(id = 2, name = "Roti Tawar", category = "Makanan", expiryDateMillis = now + (2 * oneDay), isConsumed = false), // Sisa 2 hari
//                PantryItem(3, name = "Saus Tomat", category = "Bumbu", expiryDateMillis = now - (1 * oneDay), isConsumed = false)  // Expired 1 hari
//            )
//
//            // 2. Bungkus ke dalam State
//            val dummyState = PantryState(
//                items = dummyData,
//                isLoading = false
//            )

            when (currentScreen) {
                ScreenRoute.Home -> {
                    HomeScreen(
                        state = state,
                        onNavigateToAdd = {
                            currentScreen = ScreenRoute.AddItem // Pindah ke form!
                        },
                        onDelete = { item -> viewModel.onEvent(PantryEvent.DeleteItem(item)) }
                    )
                }
                ScreenRoute.AddItem -> {
                    AddItemScreen(
                        onSave = { name, category, expiryMillis ->
                            // Tembak event untuk menyimpan ke Database
                            viewModel.onEvent(PantryEvent.SaveItem(name, category, expiryMillis,isConsumed = false))
                            // Otomatis kembali ke Home setelah menyimpan
                            currentScreen = ScreenRoute.Home
                        },
                        onNavigateBack = {
                            currentScreen = ScreenRoute.Home // Tombol back
                        }
                    )
                }
            }

            // 3. Tampilkan UI-nya
//            HomeScreen(
//                state = state,
//                onNavigateToAdd = { println("Navigasi ke halaman tambah diklik!") },
//                onDelete = { item -> println("Barang ${item.name} dihapus!") }
//            )
        }
    }
}