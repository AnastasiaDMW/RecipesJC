package ru.itis.recipesjc.model

import kotlinx.serialization.Serializable

@Serializable
data class DetailRecipeApiResponse(
    val id: Int,
    val vegan: Boolean = false,
    val glutenFree: Boolean = false,
    val cookingMinutes: Int? = 0,
    val healthScore: Int = 0,
    val instructions: String = "",
    val title: String = "",
    val image: String = "",
    val servings: Int = 0,
    val extendedIngredientApiResponses: List<ExtendedIngredientApiResponse> = emptyList(),
    val analyzedInstructions: List<AnalyzedInstructionApiResponse> = emptyList()
)
