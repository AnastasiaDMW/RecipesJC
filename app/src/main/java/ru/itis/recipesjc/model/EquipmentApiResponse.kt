package ru.itis.recipesjc.model

import kotlinx.serialization.Serializable

@Serializable
data class EquipmentApiResponse(
    val id: Int,
    val name: String,
    val image: String
)
