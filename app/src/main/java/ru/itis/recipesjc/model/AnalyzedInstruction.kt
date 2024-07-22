package ru.itis.recipesjc.model

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedInstruction(
    val steps: List<Step>
)

@Serializable
data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    val equipment: List<Equipment>
)
