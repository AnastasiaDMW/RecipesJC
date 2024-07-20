package ru.itis.recipesjc.ui.screens.home

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.itis.recipesjc.R
import ru.itis.recipesjc.model.Recipe
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
            val context = LocalContext.current
            HomeBody(
                navigateToDetailScreen,
                context,
                modifier = modifier
                    .padding(innerPadding)

            )
        }
    }
}

@Composable
fun HomeBody(
    navigateToDetailScreen: () -> Unit,
    context: Context,
    modifier: Modifier
) {
//    LazyColumn {
////        items() {
////
////        }
//    }
}

@Composable
fun RecipeItem(context: Context, recipe: Recipe) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .padding(8.dp),
            model = ImageRequest.Builder(context = context)
                .data(recipe.image)
                .crossfade(true)
                .build(),
            contentDescription = "recipe image")
        Text(
            text = recipe.title,
            fontSize = 24.sp,
            color = Color.Black
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = Modifier.width(64.dp),
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
        HomeBody({}, LocalContext.current, Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeItemPreview() {
    RecipesJCTheme {
        RecipeItem(LocalContext.current,
            Recipe(
                id = 0,
                title = "Red Lentil Soup with Chicken and Turnips",
                image = "https://img.spoonacular.com/recipes/715415-312x231.jpg",
                imageType = "jpg"
            ))
    }
}