package ru.itis.recipesjc.repository

import ru.itis.recipesjc.model.DetailRecipe
import ru.itis.recipesjc.model.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>

    suspend fun getRecipeInfo(recipeId: Int): DetailRecipe
}