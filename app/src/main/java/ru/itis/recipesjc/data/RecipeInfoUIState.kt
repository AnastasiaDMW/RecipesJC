package ru.itis.recipesjc.data

import ru.itis.recipesjc.model.DetailRecipe
import ru.itis.recipesjc.model.Recipe

sealed interface RecipeInfoUIState {
    data class Success(val data: DetailRecipe): RecipeInfoUIState
    object Error: RecipeInfoUIState
    object Loading: RecipeInfoUIState
}