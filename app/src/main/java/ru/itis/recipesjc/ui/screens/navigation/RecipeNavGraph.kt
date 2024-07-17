package ru.itis.recipesjc.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            HomeScreen({})
        }
    }
}