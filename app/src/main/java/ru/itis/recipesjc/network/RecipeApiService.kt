package ru.itis.recipesjc.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.itis.recipesjc.model.response.DetailRecipeApiResponse
import ru.itis.recipesjc.model.response.RecipeResponse

interface RecipeApiService {
    @GET("recipes/complexSearch?number=100&apiKey=d119eba3966747da8c6d63fa793d2f6f")
    suspend fun getRecipes(): RecipeResponse

    @GET("recipes/{id}/information?apiKey=d119eba3966747da8c6d63fa793d2f6f")
    suspend fun getRecipeInfo(@Path("id") recipeId: Int): DetailRecipeApiResponse
}