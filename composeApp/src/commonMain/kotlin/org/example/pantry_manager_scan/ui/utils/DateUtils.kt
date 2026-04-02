package org.example.pantry_manager_scan.ui.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.number

/**
 * Fungsi untuk merubah angka milidetik (Long) menjadi teks "DD/MM/YYYY"
 */
fun formatMillisToDateString(millis: Long): String {
    // 1. Ubah milidetik menjadi kotlinx.datetime.Instant
    val instant = Instant.fromEpochMilliseconds(millis)

    // 2. Sesuaikan dengan zona waktu HP pengguna saat ini
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    // 3. Rangkai menjadi string dengan format dua digit (misal: 05 bukan 5)
    val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
    val month = localDateTime.month.number.toString().padStart(2, '0')
    val year = localDateTime.year

    return "$day/$month/$year"
}
