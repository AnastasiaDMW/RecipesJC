package ru.itis.recipesjc.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.itis.recipesjc.ui.screens.navigation.RecipeNavGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(
    navController: NavHostController = rememberNavController()
) {
    RecipeNavGraph(navController = navController)
}