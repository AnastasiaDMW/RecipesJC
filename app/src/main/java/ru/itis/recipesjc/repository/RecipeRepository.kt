package ru.itis.recipesjc.repository

import ru.itis.recipesjc.model.entity.Recipe
import ru.itis.recipesjc.model.response.DetailRecipeApiResponse

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>

    suspend fun getRecipeInfo(recipeId: Int): DetailRecipeApiResponse
}