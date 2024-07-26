package ru.itis.recipesjc.ui.screens.detail

import android.app.Application
import android.util.Log
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
import ru.itis.recipesjc.repository.RecipeRepository
import ru.itis.recipesjc.ui.screens.home.HomeViewModel
import java.io.IOException

class DetailViewModel(
    private val application: Application,
    private val networkRecipeRepository: NetworkRecipeRepository,
    private val offlineRecipeRepository: OfflineRecipeRepository
): ViewModel() {

    var detailRecipeUiState: RecipeInfoUIState by mutableStateOf(RecipeInfoUIState.Loading)
        private set

    fun getRecipeInfo(recipeId: Int) {
        viewModelScope.launch {
            detailRecipeUiState = RecipeInfoUIState.Loading
            detailRecipeUiState = try {
                val response = networkRecipeRepository.getRecipeInfo(recipeId)
                Log.d("DATA", "Response: $response")
                RecipeInfoUIState.Success(response)
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