package ru.itis.recipesjc

import android.app.Application
import ru.itis.recipesjc.data.AppContainer
import ru.itis.recipesjc.data.DefaultAppContainer
import ru.itis.recipesjc.database.RecipeDatabase

class RecipeApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        val recipeDao = RecipeDatabase.getRecipeDao(this)
        val detailRecipeDao = RecipeDatabase.getDetailRecipeDao(this)
        val extendedIngredientDao = RecipeDatabase.getExtendedIngredientDao(this)
        container = DefaultAppContainer(recipeDao, detailRecipeDao, extendedIngredientDao)
    }
}