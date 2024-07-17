package ru.itis.recipesjc.ui.screens.navigation

import ru.itis.recipesjc.R

interface NavigationDestination {
    val route: String
    val titleRes: Int
}

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

object DetailDestination : NavigationDestination {
    override val route = "detail"
    override val titleRes = R.string.detail_screen
}