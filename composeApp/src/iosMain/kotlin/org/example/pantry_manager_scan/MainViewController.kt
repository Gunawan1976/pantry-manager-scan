package org.example.pantry_manager_scan

import androidx.compose.ui.window.ComposeUIViewController
import org.example.pantry_manager_scan.data.local.PantryDatabase
import org.example.pantry_manager_scan.di.initKoin
import org.koin.dsl.module

fun MainViewController() = ComposeUIViewController { App() }
// Fungsi ini akan dipanggil dari file iOSApp.swift di project Xcode
fun initKoinIOS() {
    initKoin(
        platformModule = module {
            single<PantryDatabase> {
                DatabaseFactory()
                    .create()
                    .build()
            }
        }
    )
}
