package org.example.pantry_manager_scan.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class OpenFoodResponse(

	@SerialName("product")
	val product: Product? = null,

	@SerialName("code")
	val code: String? = null,

	@SerialName("status_verbose")
	val statusVerbose: String? = null,

	@SerialName("status")
	val status: Int? = null
)

@Serializable
data class Product(

	@SerialName("brands_tags")
	val brandsTags: List<String?>? = null,

	@SerialName("brands")
	val brands: String? = null,

	@SerialName("quantity")
	val quantity: String? = null,

	@SerialName("brands_old")
	val brandsOld: String? = null,

	@SerialName("popularity_key")
	val popularityKey: Long? = null,

	@SerialName("purchase_places")
	val purchasePlaces: String? = null,

	@SerialName("popularity_tags")
	val popularityTags: List<String?>? = null,

	@SerialName("product_name")
	val productName: String? = null,

	@SerialName("product_quantity")
	val productQuantity: Int? = null,

	@SerialName("product_quantity_unit")
	val productQuantityUnit: String? = null,

	@SerialName("product_name_de")
	val productNameDe: String? = null,

	@SerialName("product_type")
	val productType: String? = null,

	@SerialName("product_name_ro")
	val productNameRo: String? = null,

	@SerialName("_keywords")
	val keywords: List<String?>? = null,

	@SerialName("product_name_en")
	val productNameEn: String? = null,

	@SerialName("_id")
	val id: String? = null
)
