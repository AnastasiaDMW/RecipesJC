package ru.itis.recipesjc.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.itis.recipesjc.Constant.API_KEY
import ru.itis.recipesjc.model.response.DetailRecipeApiResponse
import ru.itis.recipesjc.model.response.RecipeResponse

interface RecipeApiService {
    @GET("recipes/complexSearch?number=100&apiKey=$API_KEY")
    suspend fun getRecipes(): RecipeResponse

    @GET("recipes/{id}/information?apiKey=$API_KEY")
    suspend fun getRecipeInfo(@Path("id") recipeId: Int): DetailRecipeApiResponse
}