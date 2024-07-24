package ru.itis.recipesjc.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.itis.recipesjc.ui.screens.detail.DetailScreen
import ru.itis.recipesjc.ui.screens.detail.DetailViewModel
import ru.itis.recipesjc.ui.screens.home.HomeScreen

@Composable
fun RecipeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ){
        composable(route = HomeDestination.route) {
            HomeScreen(navController)
        }
        composable(route = DetailDestination.route+"/{id}") { navBackStack ->
            val detailViewModel: DetailViewModel = viewModel(factory = DetailViewModel.Factory)
            val recipeId = navBackStack.arguments?.getString("id")
            recipeId?.toInt()?.let { detailViewModel.getRecipeInfo(it) }
            DetailScreen(recipeId = recipeId?.toInt(), detailViewModel)
        }
    }
}