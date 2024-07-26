package ru.itis.recipesjc.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "extended_ingredients")
data class ExtendedIngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val detailRecipeId: Int,
    val image: String,
    val original: String,
    val amount: Double,
    val unit: String
)


