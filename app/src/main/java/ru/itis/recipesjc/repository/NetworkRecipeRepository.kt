package ru.itis.recipesjc.repository

import ru.itis.recipesjc.model.entity.Recipe
import ru.itis.recipesjc.model.response.DetailRecipeApiResponse
import ru.itis.recipesjc.model.response.RecipeApiResponse
import ru.itis.recipesjc.network.RecipeApiService

class NetworkRecipeRepository(
    private val recipeApiService: RecipeApiService
): RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> {
        val recipeApiResponses = recipeApiService.getRecipes().recipes
        return recipeApiResponses.map { it.toRecipe() }
    }
    override suspend fun getRecipeInfo(recipeId: Int): DetailRecipeApiResponse = recipeApiService.getRecipeInfo(recipeId)

    private fun RecipeApiResponse.toRecipe(): Recipe =
        Recipe(
            id = id,
            title = title,
            image = image
        )
}