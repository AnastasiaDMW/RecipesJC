package ru.itis.recipesjc.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.itis.recipesjc.ui.screens.navigation.RecipeNavGraph

@Composable
fun RecipeApp(
    navController: NavHostController = rememberNavController()
) {
    RecipeNavGraph(navController = navController)
}