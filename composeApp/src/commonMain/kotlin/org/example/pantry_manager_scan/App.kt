package org.example.pantry_manager_scan

import MainDashboardScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.ui.screen.HomeScreen
import org.example.pantry_manager_scan.ui.viewmodel.PantryState
import kotlinx.datetime.Clock
import org.example.pantry_manager_scan.di.sharedModule
import org.example.pantry_manager_scan.ui.screen.AddItemScreen
import org.example.pantry_manager_scan.ui.viewmodel.PantryEvent
import org.example.pantry_manager_scan.ui.viewmodel.PantryViewModel
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.compose.KoinContext

enum class ScreenRoute { Home, AddItem }

enum class BottomNavRoute(val title: String, val icon: ImageVector) {
    Home("Home", Icons.Default.Home),
    Inventory("Inventory", Icons.AutoMirrored.Filled.List),
    Resep("Resep", Icons.Default.Menu), // Gunakan icon yang mendekati dulu
    Profile("Profile", Icons.Default.Person)
}

enum class FullScreenRoute { Main, Scanner, AddItem }

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
            FullScreenRoute.Scanner -> {
                // Nanti ini diganti dengan UI Scanner sungguhan
                Box(modifier = Modifier.fillMaxSize()) {
                    Text("Ini Layar Scanner", modifier = Modifier.align(Alignment.Center))
                    Button(onClick = { currentScreen = FullScreenRoute.Main }) {
                        Text("Kembali")
                    }
                }
            }
            FullScreenRoute.AddItem -> {
                AddItemScreen(
                    onSave = { name, category, expiryMillis ->
                        viewModel.onEvent(PantryEvent.SaveItem(name, category, expiryMillis, isConsumed = false))
                        currentScreen = FullScreenRoute.Main
                    },
                    onNavigateBack = { currentScreen = FullScreenRoute.Main }
                )
            }
        }
    }
}