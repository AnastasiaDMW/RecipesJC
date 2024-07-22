package ru.itis.recipesjc.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import ru.itis.recipesjc.Constant.BASE_URL
import ru.itis.recipesjc.network.RecipeApiService
import ru.itis.recipesjc.repository.NetworkRecipeRepository
import ru.itis.recipesjc.repository.RecipeRepository

interface AppContainer {
    val recipeRepository: RecipeRepository
}

class DefaultAppContainer: AppContainer {

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            Json { ignoreUnknownKeys = true }
                .asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: RecipeApiService by lazy {
        retrofit.create(RecipeApiService::class.java)
    }

    override val recipeRepository: RecipeRepository by lazy {
        NetworkRecipeRepository(retrofitService)
    }
}