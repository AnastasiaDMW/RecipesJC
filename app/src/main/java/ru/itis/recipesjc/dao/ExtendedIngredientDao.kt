package ru.itis.recipesjc.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.itis.recipesjc.model.entity.DetailRecipeEntity
import ru.itis.recipesjc.model.entity.ExtendedIngredientEntity

@Dao
interface ExtendedIngredientDao {
    @Query("SELECT * FROM extended_ingredients")
    fun getExtendedIngredient(): Flow<List<ExtendedIngredientEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExtendedIngredient(extendedIngredient: ExtendedIngredientEntity)
}