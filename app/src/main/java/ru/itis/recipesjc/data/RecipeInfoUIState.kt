package ru.itis.recipesjc.data

import ru.itis.recipesjc.model.response.DetailRecipeApiResponse

sealed interface RecipeInfoUIState {
    data class Success(val data: DetailRecipeApiResponse): RecipeInfoUIState
    object Error: RecipeInfoUIState
    object Loading: RecipeInfoUIState
}