package org.example.pantry_manager_scan.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.pantry_manager_scan.domain.model.OpenFoodResponse

object OpenFoodFactsApi {

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true // Abaikan data API yang tidak kita butuhkan
                isLenient = true
            })
        }
    }

    suspend fun fetchProductNameFromApi(barcode: String): String? {
        return try {
            // Tembak URL API resmi Open Food Facts
            val url = "https://world.openfoodfacts.org/api/v0/product/$barcode.json"
            val response: OpenFoodResponse = httpClient.get(url).body()
            var name : String = ""

            if (response.status == 1 && response.product != null) {
                // Gabungkan Merek dan Nama Produk (Misal: "Indomie Mie Goreng")
                val brand = response.product.brands ?: ""
                name = response.product.productName ?: "Produk Tak Dikenal"
                if (brand.isNotBlank()) "$brand $name" else name
            } else {
                null // Barcode tidak ditemukan di database
            }
            return name
        } catch (e: Exception) {
            println("API Error: ${e.message}")
            null // Gagal koneksi
        }
    }

}