import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.pantry_manager_scan.BottomNavRoute
import org.example.pantry_manager_scan.ui.screen.HomeScreen
import org.example.pantry_manager_scan.ui.theme.CurvedBottomNavShape

@Composable
fun MainDashboardScreen(
    currentTab: BottomNavRoute,
    onTabSelected: (BottomNavRoute) -> Unit,
    onScannerClicked: () -> Unit,
    state: org.example.pantry_manager_scan.ui.viewmodel.PantryState,
    onNavigateToAdd: () -> Unit,
    onDelete: (org.example.pantry_manager_scan.domain.model.PantryItem) -> Unit
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

            // 1. AREA KONTEN (Layer paling bawah)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 60.dp) // Beri ruang agar konten tidak tertutup bottom bar
            ) {
                when (currentTab) {
                    BottomNavRoute.Home -> HomeScreen(state, onNavigateToAdd, onDelete)
                    BottomNavRoute.Inventory -> Text("Layar Inventory", Modifier.align(Alignment.Center))
                    BottomNavRoute.Resep -> Text("Layar Resep", Modifier.align(Alignment.Center))
                    BottomNavRoute.Profile -> Text("Layar Profile", Modifier.align(Alignment.Center))
                }
            }

            // 2. BOTTOM NAVIGATION BAR CUSTOM (Layer tengah, menempel di bawah)
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(80.dp)
                    // Menggunakan Shape Custom yang sudah kita buat
                    .shadow(0.dp, CurvedBottomNavShape(cutoutRadiusDp = 32f))
                    .clip(CurvedBottomNavShape(cutoutRadiusDp = 32f))
                    .background(Color.White)
                    .padding(horizontal = 16.dp), // Padding kiri-kanan
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Gambar ikon kiri (Home, Inventory)
                BottomNavItem(BottomNavRoute.Home, currentTab, onTabSelected)
                BottomNavItem(BottomNavRoute.Inventory, currentTab, onTabSelected)

                // Beri ruang kosong yang cukup lebar di tengah untuk cut-out FAB
                Spacer(modifier = Modifier.width(72.dp))

                // Gambar ikon kanan (Resep, Profile)
                BottomNavItem(BottomNavRoute.Resep, currentTab, onTabSelected)
                BottomNavItem(BottomNavRoute.Profile, currentTab, onTabSelected)
            }

            // 3. TOMBOL SCANNER (Layer paling atas, masuk ke dalam cut-out)
            FloatingActionButton(
                onClick = onScannerClicked,
                shape = CircleShape,
                containerColor = Color(0xFF5478FF),
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    // Posisikan tombol agar pas berada di "lubang" cut-out
                    .padding(bottom = 48.dp)
                    .size(64.dp)
            ) {
                Icon(Icons.Default.Search, contentDescription = "Scanner", modifier = Modifier.size(32.dp))
            }
        }
    }
}

// Fungsi helper untuk menggambar ikon menu agar kode lebih rapi
@Composable
fun BottomNavItem(
    route: BottomNavRoute,
    currentTab: BottomNavRoute,
    onClick: (BottomNavRoute) -> Unit
) {
    val isSelected = currentTab == route
    val color = if (isSelected) Color(0xFFFF8C00) else Color.Gray

    Column(
        modifier = Modifier
            .clickable { onClick(route) }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = route.icon, contentDescription = route.title, tint = color)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = route.title, color = color, style = MaterialTheme.typography.labelSmall)
    }
}