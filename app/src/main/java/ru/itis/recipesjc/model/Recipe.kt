package ru.itis.recipesjc.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    @SerialName("results")
    val recipes: List<Recipe>
)

@Serializable
data class Recipe(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("image") val image: String,
)