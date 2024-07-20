package ru.itis.recipesjc.data

sealed interface RecipeUiState {
    data class Success(val data: Any): RecipeUiState
    object Error: RecipeUiState
    object Loading: RecipeUiState
}