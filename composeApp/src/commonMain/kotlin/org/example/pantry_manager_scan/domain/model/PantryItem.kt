package org.example.pantry_manager_scan.domain.model

data class PantryItem(
    val id: Int = 0,
    val name: String,
    val category: String,
    val expiryDateMillis :Long,
    val isConsumed : Boolean?
){
    fun getDaysRemaining(currentDateMillis: Long):Int{
        val diffMillis = expiryDateMillis - currentDateMillis
        return (diffMillis / (1000 * 60 * 60 * 24)).toInt()
    }
}
