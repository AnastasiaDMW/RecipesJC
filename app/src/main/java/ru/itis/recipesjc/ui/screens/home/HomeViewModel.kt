package ru.itis.recipesjc.ui.screens.home

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
import ru.itis.recipesjc.data.RecipeUiState
import ru.itis.recipesjc.repository.RecipeRepository
import java.io.IOException

class HomeViewModel(
    private val recipeRepository: RecipeRepository
): ViewModel() {

    var recipeUiState: RecipeUiState by mutableStateOf(RecipeUiState.Loading)
        private set

    init {
        getRecipes()
    }

    fun getRecipes() {
        viewModelScope.launch {
            recipeUiState = RecipeUiState.Loading
            recipeUiState = try {
                RecipeUiState.Success(recipeRepository.getRecipes())
            } catch (e: IOException) {
                RecipeUiState.Error
            } catch (e: HttpException) {
                RecipeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RecipeApplication)
                val recipeRepository = application.container.recipeRepository
                HomeViewModel(recipeRepository)
            }
        }
    }
}