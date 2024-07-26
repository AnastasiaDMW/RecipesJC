package ru.itis.recipesjc.model

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedInstructionApiResponse(
    val steps: List<StepApiResponse>
)

@Serializable
data class StepApiResponse(
    val number: Int,
    val step: String,
    val ingredientApiResponses: List<IngredientApiResponse>,
    val equipmentApiResponses: List<EquipmentApiResponse>
)
