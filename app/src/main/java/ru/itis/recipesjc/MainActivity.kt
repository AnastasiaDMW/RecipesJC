package ru.itis.recipesjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import ru.itis.recipesjc.ui.RecipeApp
import ru.itis.recipesjc.ui.theme.RecipesJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backgroundColor = ContextCompat.getColor(this, R.color.background_color)

        setContent {
            RecipesJCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(backgroundColor)
                ) {
                    RecipeApp()
                }
            }
        }
    }
}