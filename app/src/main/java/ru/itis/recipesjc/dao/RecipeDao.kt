package ru.itis.recipesjc.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.itis.recipesjc.model.entity.Recipe

@Dao
interface RecipeDao{

    @Query("SELECT * FROM recipes")
    fun getRecipes(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipe(recipe: Recipe)

}