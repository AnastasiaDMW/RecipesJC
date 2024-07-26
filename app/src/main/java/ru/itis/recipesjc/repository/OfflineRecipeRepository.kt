package ru.itis.recipesjc.repository

import android.util.Log
import kotlinx.coroutines.flow.first
import ru.itis.recipesjc.dao.DetailRecipeDao
import ru.itis.recipesjc.dao.ExtendedIngredientDao
import ru.itis.recipesjc.dao.RecipeDao
import ru.itis.recipesjc.model.entity.DetailRecipeEntity
import ru.itis.recipesjc.model.entity.ExtendedIngredientEntity
import ru.itis.recipesjc.model.entity.Recipe
import ru.itis.recipesjc.model.response.DetailRecipeApiResponse
import ru.itis.recipesjc.model.response.ExtendedIngredientApiResponse

class OfflineRecipeRepository(
    private val recipeDao: RecipeDao,
    private val detailRecipeDao: DetailRecipeDao,
    private val extendedIngredientDao: ExtendedIngredientDao
): RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> {
        return recipeDao.getRecipes().first()
    }

    suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun insertDetailRecipe(detailRecipeApiResponse: DetailRecipeApiResponse) {
        detailRecipeDao.insertDetailRecipe(detailRecipeApiResponse.toDetailRecipeEntity())
    }

    suspend fun insertExtendedInstructions(detailRecipeApiResponse: DetailRecipeApiResponse) {
        for (expandedInstruction in detailRecipeApiResponse.extendedIngredientApiResponses) {
            extendedIngredientDao.insertExtendedIngredient(
                ExtendedIngredientEntity(
                id = expandedInstruction.id,
                original = expandedInstruction.original,
                image = expandedInstruction.image,
                unit = expandedInstruction.unit,
                amount = expandedInstruction.amount,
                detailRecipeId = detailRecipeApiResponse.id
            )
            )
        }
    }

    override suspend fun getRecipeInfo(recipeId: Int): DetailRecipeApiResponse {
        val recipeInfo = detailRecipeDao.getDetailRecipes().first().filter { it.id == recipeId }[0]
        Log.d("DATA", "recipeInfo: $recipeInfo")
        val extendedIngredient = extendedIngredientDao.getExtendedIngredient().first().filter { it.detailRecipeId == recipeInfo.id }
        Log.d("DATA", "extendedIngredient: $extendedIngredient")


        return DetailRecipeApiResponse(
            id = recipeInfo.id,
            vegan = recipeInfo.vegan,
            glutenFree = recipeInfo.glutenFree,
            cookingMinutes = recipeInfo.cookingMinutes,
            healthScore = recipeInfo.healthScore,
            instructions = recipeInfo.instructions,
            title = recipeInfo.title,
            image = recipeInfo.image,
            servings = recipeInfo.servings,
            extendedIngredientApiResponses = extendedIngredient.map { it.toExtendedIngredientApiResponse() }
        )
    }

    private fun ExtendedIngredientEntity.toExtendedIngredientApiResponse(): ExtendedIngredientApiResponse =
        ExtendedIngredientApiResponse(
            id = id,
            image = image,
            original = original,
            amount = amount,
            unit = unit
        )

    private fun DetailRecipeApiResponse.toDetailRecipeEntity(): DetailRecipeEntity =
        DetailRecipeEntity(
            id = id,
            vegan = vegan,
            glutenFree = glutenFree,
            cookingMinutes = cookingMinutes,
            healthScore = healthScore,
            instructions = instructions,
            title = title,
            image = image,
            servings = servings
        )
}