package org.example.pantry_manager_scan.di

import org.example.pantry_manager_scan.data.local.PantryDatabase
import org.example.pantry_manager_scan.data.repository.PantryRepositoryImpl
import org.example.pantry_manager_scan.domain.repository.PantryRepository
import org.example.pantry_manager_scan.ui.viewmodel.PantryViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {
    single { get<PantryDatabase>().pantryDao() }
    single<PantryRepository> { PantryRepositoryImpl(get()) }
    factory { PantryViewModel(get()) }
}

fun initKoin(platformModule: Module) {
    startKoin {
        modules(platformModule, sharedModule)
    }
}