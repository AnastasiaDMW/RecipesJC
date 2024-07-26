package ru.itis.recipesjc.repository

import ru.itis.recipesjc.model.DetailRecipe
import ru.itis.recipesjc.model.DetailRecipeApiResponse
import ru.itis.recipesjc.model.Recipe
import ru.itis.recipesjc.model.RecipeApiResponse
import ru.itis.recipesjc.model.RecipeResponse
import ru.itis.recipesjc.network.RecipeApiService

class NetworkRecipeRepository(
    private val recipeApiService: RecipeApiService
): RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> {
        val recipeApiResponses = recipeApiService.getRecipes().recipes
        return recipeApiResponses.map { it.toRecipe() }
    }
    override suspend fun getRecipeInfo(recipeId: Int): DetailRecipe = recipeApiService.getRecipeInfo(recipeId).toDetailRecipe()

    private fun RecipeApiResponse.toRecipe(): Recipe =
        Recipe(
            id = id,
            title = title,
            image = image
        )

    private fun DetailRecipeApiResponse.toDetailRecipe(): DetailRecipe =
        DetailRecipe(
            id = id,
            title = title,
            healthScore = healthScore,
            glutenFree = glutenFree,
            vegan = vegan,
            servings = servings,
            image = image,
            cookingMinutes = cookingMinutes,
            extendedIngredientApiResponses = extendedIngredientApiResponses,
            analyzedInstructions = analyzedInstructions
        )
}