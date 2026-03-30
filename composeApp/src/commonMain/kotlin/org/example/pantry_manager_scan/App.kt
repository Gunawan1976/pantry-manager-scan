package org.example.pantry_manager_scan

import MainDashboardScreen
import org.example.pantry_manager_scan.ui.screen.ScannerScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import org.example.pantry_manager_scan.ui.screen.AddItemScreen
import org.example.pantry_manager_scan.ui.screen.InventoryScreen
import org.example.pantry_manager_scan.ui.viewmodel.PantryEvent
import org.example.pantry_manager_scan.ui.viewmodel.PantryViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import pantrymanagerscan.composeapp.generated.resources.Res
import pantrymanagerscan.composeapp.generated.resources.ic_inventory
import pantrymanagerscan.composeapp.generated.resources.ic_receipt

enum class ScreenRoute { Home, AddItem }

enum class BottomNavRoute(
    val title: String,
    // Tambahkan Color sebagai parameter yang akan diterima oleh lambda ini
    val icon: @Composable (tint: Color) -> Unit
) {
    Home(
        title = "Home",
        icon = { tint -> Icon(imageVector = Icons.Default.Home, contentDescription = "Home", tint = tint) }
    ),
    Inventory(
        title = "Inventory",
        icon = { tint -> Icon(painterResource(Res.drawable.ic_inventory), contentDescription = "Inventory", tint = tint) }
    ),
    Resep(
        title = "Resep",
        icon = { tint -> Icon(painterResource(Res.drawable.ic_receipt), contentDescription = "Inventory", tint = tint) }
    ),
    Profile(
        title = "Profile",
        icon = { tint -> Icon(imageVector = Icons.Default.Person, contentDescription = "Profile", tint = tint) }
    )
}

enum class FullScreenRoute { Main, Scanner, AddItem,Inventory }

@Composable
fun App() {
    MaterialTheme {
        // 2. Minta ViewModel dari Koin secara otomatis! (Keajaiban DI)
        val viewModel = koinViewModel<PantryViewModel>()

        // 3. Dengarkan perubahan State dari ViewModel (Mirip BlocBuilder/watch)
        val state by viewModel.state.collectAsState()

        // State untuk navigasi layar penuh (App Level)
        var currentScreen by remember { mutableStateOf(FullScreenRoute.Main) }

        // State untuk navigasi tab di bawah (hanya aktif kalau currentScreen == Main)
        var currentBottomTab by remember { mutableStateOf(BottomNavRoute.Home) }

        when (currentScreen) {
            FullScreenRoute.Main -> {
                // Tampilkan Scaffold dengan Bottom Nav
                MainDashboardScreen(
                    currentTab = currentBottomTab,
                    onTabSelected = { currentBottomTab = it },
                    // Teruskan data ke HomeScreen
                    state = state,
                    onNavigateToAdd = { currentScreen = FullScreenRoute.AddItem },
                    onDelete = { item -> viewModel.onEvent(PantryEvent.DeleteItem(item)) },
                    onScannerClicked = { currentScreen = FullScreenRoute.Scanner }
                )
            }
            FullScreenRoute.Inventory -> {
                // Tampilkan Scaffold dengan Bottom Nav
                InventoryScreen(

                    // Teruskan data ke HomeScreen
                    state = state,
                    onNavigateToAdd = { currentScreen = FullScreenRoute.AddItem },
                    onDelete = { item -> viewModel.onEvent(PantryEvent.DeleteItem(item)) },
                )
            }
            FullScreenRoute.Scanner -> {
                // Panggil UI Scanner yang baru dibuat
                ScannerScreen(
                    viewModel = viewModel,
                    onClose = {
                        currentScreen = FullScreenRoute.Main // Kembali ke dashboard
                    },
                    onSave = { name, category, expiryMillis, qty,currentBarcode ->
                        viewModel.onEvent(PantryEvent.SaveItem(name, category, expiryMillis,isConsumed = false, quantity = qty, barcode =currentBarcode ))
                        currentScreen = FullScreenRoute.Main
                    }
                )
            }
            FullScreenRoute.AddItem -> {
                AddItemScreen(
                    onSave = { name, category, expiryMillis,qty ->
                        viewModel.onEvent(PantryEvent.SaveItem(name, category, expiryMillis, isConsumed = false, quantity = qty, barcode = null))
                        currentScreen = FullScreenRoute.Main
                    },
                    onNavigateBack = { currentScreen = FullScreenRoute.Main }
                )
            }
        }
    }
}