package ru.itis.recipesjc.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.itis.recipesjc.R
import ru.itis.recipesjc.data.RecipeInfoUIState
import ru.itis.recipesjc.model.response.DetailRecipeApiResponse
import ru.itis.recipesjc.model.response.ExtendedIngredientApiResponse
import ru.itis.recipesjc.ui.screens.home.ErrorScreen
import ru.itis.recipesjc.ui.screens.home.LoadingScreen
import ru.itis.recipesjc.ui.theme.RecipesJCTheme
import java.util.Locale

@Composable
fun DetailScreen(recipeId: Int?, detailViewModel: DetailViewModel, modifier: Modifier = Modifier) {
    val detailRecipeUiState = detailViewModel.detailRecipeUiState
    RecipesJCTheme {
        Scaffold(
            containerColor = colorResource(id = R.color.background_color),
            modifier = modifier
        ){  innerPadding ->
            when (detailRecipeUiState) {
                is RecipeInfoUIState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
                is RecipeInfoUIState.Error -> ErrorScreen(
                    retryAction = {
                        if (recipeId != null) {
                            detailViewModel.getRecipeInfo(recipeId)
                        }
                    },
                    modifier = modifier.fillMaxSize()
                )
                is RecipeInfoUIState.Success -> DetailBody(
                    recipeInfo = detailRecipeUiState.data,
                    contentPadding = innerPadding,
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun DetailBody(
    recipeInfo: DetailRecipeApiResponse,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .height(248.dp),
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(recipeInfo.image)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.recipe_img)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = recipeInfo.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.gluten_free),
                contentDescription = stringResource(R.string.gluten_free_icon)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.gluten_free_tv)+" "+if (recipeInfo.glutenFree) stringResource(
                    id = R.string.yes_tv) else stringResource(R.string.no_tv),
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(39.dp),
                painter = painterResource(R.drawable.vegan),
                contentDescription = stringResource(R.string.ic_vegan)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.vegan_tv)+" "+if (recipeInfo.vegan) stringResource(
                    id = R.string.yes_tv) else stringResource(R.string.no_tv),
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(39.dp),
                painter = painterResource(R.drawable.time),
                contentDescription = stringResource(R.string.ic_cooking_minutes)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.cooking_minutes_tv)+" ${recipeInfo.cookingMinutes}",
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(39.dp),
                painter = painterResource(R.drawable.health),
                contentDescription = stringResource(R.string.ic_health_score)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.health_score_tv)+" ${recipeInfo.healthScore}",
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(39.dp),
                painter = painterResource(R.drawable.serving),
                contentDescription = stringResource(R.string.ic_servings)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.servings_tv)+" ${recipeInfo.servings}",
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.instruction_tv)+" ${removeHtmlTags(recipeInfo.instructions)}",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.ingredients_tv),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = recipeInfo.extendedIngredientApiResponses,
                key = { ingredient -> ingredient.id }
            ) {
                IngredientItem(it)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

fun removeHtmlTags(text: String): String {
    return text.replace(Regex("</?\\w+[^>]*>"), "")
}

@Composable
fun IngredientItem(extendedIngredientApiResponse: ExtendedIngredientApiResponse) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .padding(start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .height(124.dp)
                .clip(RoundedCornerShape(16.dp)),
            model = "https://spoonacular.com/cdn/ingredients_100x100/${extendedIngredientApiResponse.image}?apiKey=d119eba3966747da8c6d63fa793d2f6f",
            contentDescription = stringResource(R.string.ingredient_img),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = extendedIngredientApiResponse.original.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "${extendedIngredientApiResponse.amount} ${extendedIngredientApiResponse.unit}"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailBodyPreview() {
    RecipesJCTheme {
        DetailBody(
            recipeInfo = DetailRecipeApiResponse(
                id = 0,
                title = "Грибная запеканка",
                vegan = false,
                glutenFree = false,
                cookingMinutes = 55,
                healthScore = 100,
                instructions = "Saute the onions in the EVOO, adding the garlic after a couple of minutes; cook until the onions are translucent. </li><li>Add the whole bag of asparagus and cover everything with the broth. </li><li>Season with salt and pepper and a pinch of red pepper flakes, if using.",
                extendedIngredientApiResponses = listOf(
                    ExtendedIngredientApiResponse(0, "", "avocado",5.0,"servings"),
                    ExtendedIngredientApiResponse(1, "", "avocado",5.0,"servings")
                ),
            ),
            contentPadding = PaddingValues(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ingredientItemPreview() {
    RecipesJCTheme {
        IngredientItem(extendedIngredientApiResponse = ExtendedIngredientApiResponse(0, "", "avocado",5.0,"servings"))
    }
}