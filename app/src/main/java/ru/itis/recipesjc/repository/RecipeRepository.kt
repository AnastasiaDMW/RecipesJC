package ru.itis.recipesjc.repository

import ru.itis.recipesjc.model.Recipe

interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
}