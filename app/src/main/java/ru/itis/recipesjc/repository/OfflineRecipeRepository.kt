package ru.itis.recipesjc.repository

import kotlinx.coroutines.flow.first
import ru.itis.recipesjc.dao.RecipeDao
import ru.itis.recipesjc.model.DetailRecipe
import ru.itis.recipesjc.model.Recipe
import ru.itis.recipesjc.network.RecipeApiService

class OfflineRecipeRepository(
    private val recipeDao: RecipeDao
): RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> {
        return recipeDao.getRecipes().first()
    }

    suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun getRecipeInfo(recipeId: Int): DetailRecipe {
        return DetailRecipe(0)
    }
}