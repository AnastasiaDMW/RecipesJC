package ru.itis.recipesjc.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.itis.recipesjc.model.entity.DetailRecipeEntity

@Dao
interface DetailRecipeDao {

    @Query("SELECT * FROM detail_recipes")
    fun getDetailRecipes(): Flow<List<DetailRecipeEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDetailRecipe(detailRecipe: DetailRecipeEntity)
}