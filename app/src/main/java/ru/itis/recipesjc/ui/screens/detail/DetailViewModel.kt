package ru.itis.recipesjc.ui.screens.detail

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
import ru.itis.recipesjc.data.RecipeInfoUIState
import ru.itis.recipesjc.repository.RecipeRepository
import java.io.IOException

class DetailViewModel(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    var detailRecipeUiState: RecipeInfoUIState by mutableStateOf(RecipeInfoUIState.Loading)
        private set

    fun getRecipeInfo(recipeId: Int) {
        viewModelScope.launch {
            detailRecipeUiState = RecipeInfoUIState.Loading
            detailRecipeUiState = try {
                RecipeInfoUIState.Success(recipeRepository.getRecipeInfo(recipeId))
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
                val recipeRepository = application.container.recipeRepository
                DetailViewModel(recipeRepository)
            }
        }
    }
}