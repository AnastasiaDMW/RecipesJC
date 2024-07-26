package ru.itis.recipesjc.ui.screens.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.itis.recipesjc.RecipeApplication
import ru.itis.recipesjc.data.DefaultAppContainer
import ru.itis.recipesjc.data.RecipeUiState
import ru.itis.recipesjc.database.RecipeDatabase
import ru.itis.recipesjc.model.Recipe
import ru.itis.recipesjc.repository.NetworkRecipeRepository
import ru.itis.recipesjc.repository.OfflineRecipeRepository
import ru.itis.recipesjc.repository.RecipeRepository
import java.io.IOException

class HomeViewModel(
    private val application: Application,
    private val networkRecipeRepository: NetworkRecipeRepository,
    private val offlineRecipeRepository: OfflineRecipeRepository
): ViewModel() {

    var recipeUiState: RecipeUiState by mutableStateOf(RecipeUiState.Loading)
        private set

    init {
        getRecipes()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun getRecipes() {
        viewModelScope.launch {
            recipeUiState = RecipeUiState.Loading
            val recipesFromDb = offlineRecipeRepository.getRecipes()
            try {
                if (isNetworkAvailable(application)) {
                    val recipesFromApi = networkRecipeRepository.getRecipes()
                    if (recipesFromDb.isEmpty()) {
                        for (recipe in recipesFromApi) {
                            offlineRecipeRepository.insertRecipe(recipe)
                        }
                        recipeUiState = RecipeUiState.Success(recipesFromApi)
                    }
                    else if (recipesFromDb.size < recipesFromApi.size) {
                        for (recipeApi in recipesFromApi) {
                            for (recipeDB in recipesFromDb) {
                                if (recipeApi.id != recipeDB.id) {
                                    offlineRecipeRepository.insertRecipe(recipeApi)
                                }
                            }
                        }
                    }
                    else {
                        recipeUiState = RecipeUiState.Success(recipesFromDb)
                    }
                } else {
                    recipeUiState = RecipeUiState.Success(recipesFromDb)
                }
            } catch (e: IOException) {
                recipeUiState = RecipeUiState.Error
            } catch (e: HttpException) {
                recipeUiState = RecipeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RecipeApplication)
                val recipeDao = RecipeDatabase.getRecipeDao(application.applicationContext)
                val appContainer = DefaultAppContainer(recipeDao)
                HomeViewModel(
                    application,
                    appContainer.networkRecipeRepository,
                    appContainer.offlineRecipeRepository
                )
            }
        }
    }
}