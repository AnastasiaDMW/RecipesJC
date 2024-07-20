package ru.itis.recipesjc.network

import retrofit2.http.GET
import ru.itis.recipesjc.model.Recipe

interface RecipeApiService {
    @GET("/recipes/complexSearch?number=100&apiKey=d119eba3966747da8c6d63fa793d2f6f")
    suspend fun getRecipes(): List<Recipe>
}