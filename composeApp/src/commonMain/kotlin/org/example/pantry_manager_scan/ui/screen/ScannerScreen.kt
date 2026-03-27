package org.example.pantry_manager_scan.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.example.pantry_manager_scan.data.remote.OpenFoodFactsApi.fetchProductNameFromApi
import org.example.pantry_manager_scan.ui.components.CameraPreview
import org.example.pantry_manager_scan.ui.utils.formatMillisToDateString
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ScannerScreen(
    onClose: () -> Unit,
    onSave: (name: String, category: String, expiryMillis: Long, qty: Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Kulkas") }
    var quantity by remember { mutableStateOf(1) }

    var expiryDateMillis by remember { mutableStateOf(Clock.System.now().toEpochMilliseconds()) }

    var isScanning by remember { mutableStateOf(true) }

    val categoryList = listOf("Kulkas", "Lemari Kering", "Bumbu", "Minuman")


    // Warna dari desainmu
    val OrangePrimary = Color(0xFFE65100)

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF2C2C2C))) { // Warna gelap ala kamera
        if (isScanning) {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                onBarcodeScanned = { scannedCode ->
                    if (isScanning) {
                        isScanning = false // Kunci scanner
                        name = "Mencari data: $scannedCode..." // Teks loading sementara

                        // Jalankan pemanggilan API di background
                        coroutineScope.launch {
                            val apiResult = fetchProductNameFromApi(scannedCode)
                            if (apiResult != null) {
                                name = apiResult // Ganti nama dengan hasil dari internet!
                            } else {
                                name = "Tidak ditemukan ($scannedCode)" // Gagal, biarkan user ketik manual
                            }
                        }
                    }
                }
            )
        } else {
            // Jika sudah berhasil scan, bekukan layarnya (tampilkan background gelap sementara)
            Box(modifier = Modifier.fillMaxSize().background(Color(0xFF2C2C2C))) {
                Text(
                    text = "Barcode Terkunci: $name\n(Klik ikon X untuk membatalkan)",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        // --- 1. AREA MOCKUP KAMERA (Background) ---
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // Kotak Reticle Scanner (Garis Oranye)
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .border(4.dp, OrangePrimary, RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                // Garis laser scanning di tengah
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(OrangePrimary)
                        .padding(horizontal = 16.dp)
                )
            }
        }

        // Tombol Silang (Close) di pojok kanan atas
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 16.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Tutup",
            )
        }

        // --- 2. FORM BOTTOM SHEET (Diikat ke bawah) ---
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                // Agar pas diketik keyboard, formnya bisa ikut naik
                .imePadding(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Garis abu-abu kecil di tengah atas (handle)
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.LightGray)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Input Nama Barang
                Text("Nama Barang", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text("Susu UHT Full Cream") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // Input Kategori
//                Text("Kategori & Lokasi", fontWeight = FontWeight.Bold, fontSize = 14.sp)
//                OutlinedTextField(
//                    value = category,
//                    onValueChange = { category = it },
//                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(12.dp),
////                    trailingIcon = {  androidx.compose.material3.Icon(
////                        imageVector = Icons.Default.ArrowDropDown,
////                        contentDescription = "Tutup",
////                        modifier = Modifier
////                            .size(75.dp)
////                            .clickable { }
////                    ) }
//                )

                // Baris Kuantitas
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Kuantitas", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                    // Tombol Minus & Plus
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedButton(
                            onClick = { if (quantity > 1) quantity-- },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(40.dp)
                        ) { Text("-", fontSize = 20.sp, color = Color.Black) }

                        Text(
                            text = "$quantity",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        OutlinedButton(
                            onClick = { quantity++ },
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(40.dp)
                        ) { Text("+", fontSize = 20.sp, color = Color.Black) }
                    }
                }

                // Baris Tanggal Kedaluwarsa
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Tanggal Kedaluwarsa", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(
                        text = formatMillisToDateString(expiryDateMillis),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                }

                Text("Kategori & Lokasi", fontWeight = FontWeight.Bold, fontSize = 14.sp)

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(categoryList) { cat ->
                        val isSelected = category == cat

                        FilterChip(
                            selected = isSelected,
                            onClick = { category = cat },
                            label = {
                                Text(
                                    text = cat,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFFFF8C00).copy(alpha = 0.2f),
                                selectedLabelColor = Color(0xFFFF8C00)
                            ),
                            border = FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected = isSelected,
                                borderColor = if (isSelected) Color(0xFFA39D9D) else Color(0xFFFF8C00)
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }

                // Tombol Tambah Hari Cepat (+1 Minggu, +1 Bulan)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val oneDay = 86400000L
                    OutlinedButton(
                        onClick = { expiryDateMillis += (7 * oneDay) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("+1 Minggu", fontSize = 12.sp, color = Color.Black) }

                    OutlinedButton(
                        onClick = { expiryDateMillis += (30 * oneDay) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("+1 Bulan", fontSize = 12.sp, color = Color.Black) }

                    OutlinedButton(
                        onClick = { expiryDateMillis += (180 * oneDay) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("+6 Bulan", fontSize = 12.sp, color = Color.Black) }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tombol Simpan
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Tombol Pertama
                    Button(
                        onClick = { onSave(name, category, expiryDateMillis, quantity) },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = OrangePrimary),
                        shape = RoundedCornerShape(16.dp),
                        enabled = name.isNotBlank()
                    ) {
                        Text("Simpan", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            if (!isScanning) {
                                // Kalau sedang terkunci, reset form dan buka kunci scanner
                                isScanning = true
                                name = ""
                            } else {
                                // Kalau memang sedang scanning, tutup layar Scanner kembali ke Home
                                onClose()
                            }
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Reset", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp)) // Jarak aman bawah
            }
        }
    }
}

@Preview
@Composable
fun ScannerScreenPreview() {
    MaterialTheme {
        ScannerScreen(
            onClose = {},
            onSave = { name, category, expiryMillis, qty ->
                // Handle save logic here
            }
        )
    }
}