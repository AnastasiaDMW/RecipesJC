package ru.itis.recipesjc.data

import ru.itis.recipesjc.model.entity.Recipe

sealed interface RecipeUiState {
    data class Success(val data: List<Recipe>): RecipeUiState
    object Error: RecipeUiState
    object Loading: RecipeUiState
}