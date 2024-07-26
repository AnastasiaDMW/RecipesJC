package ru.itis.recipesjc.model

import kotlinx.serialization.Serializable

@Serializable
data class ExtendedIngredientApiResponse(
    val id: Int,
    val image: String,
    val original: String,
    val amount: Double,
    val unit: String
)