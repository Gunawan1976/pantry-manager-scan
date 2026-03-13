package org.example.pantry_manager_scan.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.pantry_manager_scan.domain.model.PantryItem

@Composable
fun UrgentItemCard(item: PantryItem, currentTime: Long) {
    val daysRemaining = item.getDaysRemaining(currentTime)

    // Tentukan teks dan warna berdasarkan sisa hari
    val (statusText, statusColor) = when {
        daysRemaining < 0 -> "Expired!" to Color.Red
        daysRemaining == 0 -> "Hari ini!" to Color.Red
        else -> "$daysRemaining days left" to Color(0xFFFF8C00) // Warna Oranye
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.size(width = 110.dp, height = 130.dp) // Ukuran kotak
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            // Karena kita belum punya gambar, kita pakai emoji / ikon sementara
            Text(text = "📦", fontSize = 40.sp)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = statusText,
                color = statusColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}