package ru.itis.recipesjc.model

import kotlinx.serialization.Serializable

@Serializable
data class DetailRecipe(
    val id: Int,
    val vegan: Boolean,
    val glutenFree: Boolean,
    val cookingMinutes: Int,
    val healthScore: Int,
    val instructions: String,
    val title: String,
    val image: String,
    val servings: Int,
    val extendedIngredients: List<ExtendedIngredient>,
    val analyzedInstructions: List<AnalyzedInstruction>
)
