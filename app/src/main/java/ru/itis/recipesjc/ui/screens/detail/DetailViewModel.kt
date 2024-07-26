package ru.itis.recipesjc.ui.screens.detail

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.itis.recipesjc.RecipeApplication
import ru.itis.recipesjc.data.DefaultAppContainer
import ru.itis.recipesjc.data.RecipeInfoUIState
import ru.itis.recipesjc.database.RecipeDatabase
import ru.itis.recipesjc.repository.NetworkRecipeRepository
import ru.itis.recipesjc.repository.OfflineRecipeRepository
import java.io.IOException

class DetailViewModel(
    private val application: Application,
    private val networkRecipeRepository: NetworkRecipeRepository,
    private val offlineRecipeRepository: OfflineRecipeRepository
): ViewModel() {

    var detailRecipeUiState: RecipeInfoUIState by mutableStateOf(RecipeInfoUIState.Loading)
        private set

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun getRecipeInfo(recipeId: Int) {
        viewModelScope.launch {
            detailRecipeUiState = RecipeInfoUIState.Loading
            val recipeFromDb = offlineRecipeRepository.getRecipeInfo(recipeId)
            try {
                if (isNetworkAvailable(application)) {
                    val recipesFromApi = networkRecipeRepository.getRecipeInfo(recipeId)
                    if (recipeFromDb.id != recipeId) {
                        offlineRecipeRepository.insertDetailRecipe(recipesFromApi)
                        offlineRecipeRepository.insertExtendedInstructions(recipesFromApi)

                        detailRecipeUiState = RecipeInfoUIState.Success(recipesFromApi)
                    }
                    else {
                        detailRecipeUiState = RecipeInfoUIState.Success(recipeFromDb)
                    }
                } else {
                    detailRecipeUiState = RecipeInfoUIState.Success(recipeFromDb)
                }
            } catch (e: IOException) {
                RecipeInfoUIState.Error
            } catch (e: HttpException) {
                RecipeInfoUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RecipeApplication)
                val recipeDao = RecipeDatabase.getRecipeDao(application.applicationContext)
                val detailRecipeDao = RecipeDatabase.getDetailRecipeDao(application.applicationContext)
                val extendedIngredientDao = RecipeDatabase.getExtendedIngredientDao(application.applicationContext)
                val appContainer = DefaultAppContainer(recipeDao, detailRecipeDao, extendedIngredientDao)
                DetailViewModel(
                    application,
                    appContainer.networkRecipeRepository,
                    appContainer.offlineRecipeRepository
                )
            }
        }
    }
}