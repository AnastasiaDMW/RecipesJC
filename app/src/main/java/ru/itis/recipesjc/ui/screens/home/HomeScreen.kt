package ru.itis.recipesjc.ui.screens.home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.itis.recipesjc.R
import ru.itis.recipesjc.data.RecipeUiState
import ru.itis.recipesjc.model.Recipe
import ru.itis.recipesjc.ui.theme.RecipesJCTheme

@Composable
fun HomeScreen(
    navigateToDetailScreen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val recipeUiState = homeViewModel.recipeUiState
    RecipesJCTheme {
        Scaffold(
            containerColor = colorResource(id = R.color.background_color),
            modifier = modifier
        ){  innerPadding ->
            when (recipeUiState) {
                is RecipeUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
                is RecipeUiState.Error -> ErrorScreen(
                    retryAction = homeViewModel::getRecipes,
                    modifier = modifier.fillMaxSize()
                )
                is RecipeUiState.Success -> HomeBody(
                    navigateToDetailScreen = navigateToDetailScreen,
                    recipes = recipeUiState.data,
                    contentPadding = innerPadding,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun HomeBody(
    navigateToDetailScreen: () -> Unit,
    recipes: List<Recipe>,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier
) {
    var searchText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search icon" )
            },
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp),
            shape = RoundedCornerShape(50.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Search") },
            placeholder = { Text(text = "Search") }
        )
        LazyColumn(
            modifier = modifier.padding(8.dp),
            contentPadding = contentPadding
        ) {
            items(items = recipes, key = { recipe -> recipe.id }) {
                RecipeItem(
                    recipe = it,
                    navigateToDetailScreen,
                )
            }
        }
    }
}

@Composable
fun RecipeItem(
    recipe: Recipe,
    navigateToDetailScreen: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)
            .clickable { navigateToDetailScreen() }
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .height(248.dp),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(recipe.image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "recipe image")
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = recipe.title,
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = MaterialTheme.colorScheme.errorContainer,
        trackColor = MaterialTheme.colorScheme.surface
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = "error image"
        )
        Text(
            text = "Failed to load",
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(text = "Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    RecipesJCTheme {
        HomeBody({}, listOf(), modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() {
    RecipesJCTheme {
        RecipeItem(
            Recipe(
                id = 0,
                title = "Red Lentil Soup with Chicken and Turnips",
                image = "https://img.spoonacular.com/recipes/715415-312x231.jpg"
            ),{})
    }
}