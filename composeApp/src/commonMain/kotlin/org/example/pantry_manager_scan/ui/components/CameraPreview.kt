package org.example.pantry_manager_scan.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Kita buat janji bahwa komponen ini akan disediakan oleh platform masing-masing
@Composable
expect fun CameraPreview(modifier: Modifier = Modifier,onBarcodeScanned: (String) -> Unit)