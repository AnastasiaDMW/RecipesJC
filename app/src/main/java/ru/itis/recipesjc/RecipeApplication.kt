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
        container = DefaultAppContainer(recipeDao)
    }
}