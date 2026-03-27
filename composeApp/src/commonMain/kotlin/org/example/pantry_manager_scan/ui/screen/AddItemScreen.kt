package org.example.pantry_manager_scan.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.pantry_manager_scan.domain.model.PantryItem
import org.example.pantry_manager_scan.ui.viewmodel.PantryState
import kotlinx.datetime.Clock
import org.example.pantry_manager_scan.ui.utils.formatMillisToDateString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(
    onSave: (name: String, category: String, expiryMillis: Long,qty:Int) -> Unit,
    onNavigateBack: () -> Unit
){
    var name by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var daysToExpire by remember { mutableStateOf("") }
    var quantityValue by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    val categoryList = listOf("Kulkas", "Lemari Kering", "Bumbu", "Minuman")


    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = { Text("Tambah Barang") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues).padding(16.dp).fillMaxSize() ,
        ){
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Barang (misal: Susu UHT)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = quantityValue,
                onValueChange = { quantityValue = it },
                label = { Text("Jumlah barang (misal: 0)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = if (selectedDateMillis != null) {
                    formatMillisToDateString(selectedDateMillis!!)
                } else {
                    ""
                },
                onValueChange = { },
                label = { Text("Tanggal Kedaluwarsa") },
                placeholder = { Text("Pilih tanggal di kalender") },
                enabled = false, // Disable pengetikan manual
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true }, // Munculkan dialog saat diklik
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                trailingIcon = {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Buka Kalender")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Kategori & Lokasi", fontWeight = FontWeight.Bold, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

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

            Spacer(modifier = Modifier.weight(1f)) // Mendorong tombol ke paling bawah

            Button(
                onClick = {
                    if (name.isNotBlank() && selectedDateMillis != null) {
                        // Tidak perlu dihitung manual lagi, karena DatePicker langsung
                        // memberikan format UTC Epoch Milliseconds!
                        onSave(name, category, selectedDateMillis!!,quantityValue.toInt())
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && selectedDateMillis != null
            ) {
                Text("Simpan ke Pantry")
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Ambil waktu yang dipilih dari state kalender
                        selectedDateMillis = datePickerState.selectedDateMillis
                        showDatePicker = false
                    },
                    // Tombol OK hanya bisa diklik kalau user sudah milih tanggal
                    enabled = datePickerState.selectedDateMillis != null
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Batal")
                }
            }
        ) {
            // Komponen Kalender Material 3
            DatePicker(state = datePickerState)
        }
    }
}

@Preview
@Composable
fun AddItemScreenPreview() {
    MaterialTheme {
        AddItemScreen(
            onSave = { name, category, expiryMillis,qty->
                // Handle save logic here
            },
            onNavigateBack = {}
        )
    }
}