package ru.itis.recipesjc

import android.app.Application
import ru.itis.recipesjc.data.AppContainer
import ru.itis.recipesjc.data.DefaultAppContainer

class RecipeApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}