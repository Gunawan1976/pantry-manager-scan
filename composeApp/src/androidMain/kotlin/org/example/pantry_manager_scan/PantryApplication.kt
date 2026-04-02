package org.example.pantry_manager_scan

import android.app.Application
import org.example.pantry_manager_scan.data.local.PantryDatabase
import org.example.pantry_manager_scan.di.initKoin
import org.koin.dsl.module

class PantryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Panggil initKoin dan berikan Database spesifik Android
        initKoin(
            platformModule = module {
                single<PantryDatabase> {
                    DatabaseFactory(this@PantryApplication)
                        .create()
                        .build()
                }
            }
        )
    }
}