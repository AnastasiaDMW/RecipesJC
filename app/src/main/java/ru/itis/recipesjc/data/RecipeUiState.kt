package ru.itis.recipesjc.data

import ru.itis.recipesjc.model.Recipe
import ru.itis.recipesjc.model.RecipeResponse

sealed interface RecipeUiState {
    data class Success(val data: List<Recipe>): RecipeUiState
    object Error: RecipeUiState
    object Loading: RecipeUiState
}