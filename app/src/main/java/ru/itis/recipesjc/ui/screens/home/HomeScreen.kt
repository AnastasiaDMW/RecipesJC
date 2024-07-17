package ru.itis.recipesjc.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import ru.itis.recipesjc.R
import ru.itis.recipesjc.ui.theme.RecipesJCTheme

@Composable
fun HomeScreen(
    navigateToDetailScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    RecipesJCTheme {
        Scaffold(
            containerColor = colorResource(id = R.color.background_color),
            modifier = modifier
        ){  innerPadding ->

            HomeBody(
                navigateToDetailScreen,
                modifier = modifier
                    .padding(innerPadding)

            )
        }
    }
}

@Composable
fun HomeBody(
    navigateToDetailScreen: () -> Unit,
    modifier: Modifier
) {

}

@Composable
fun RecipeItem() {

}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    RecipesJCTheme {
        HomeBody({}, Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() {
    RecipesJCTheme {
        RecipeItem()
    }
}