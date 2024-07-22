package ru.itis.recipesjc.repository

import ru.itis.recipesjc.model.Recipe
import ru.itis.recipesjc.model.RecipeResponse
import ru.itis.recipesjc.network.RecipeApiService

class NetworkRecipeRepository(
    private val recipeApiService: RecipeApiService
): RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> = recipeApiService.getRecipes().recipes
}