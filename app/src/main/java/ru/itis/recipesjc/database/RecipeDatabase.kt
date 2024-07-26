package ru.itis.recipesjc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.itis.recipesjc.dao.DetailRecipeDao
import ru.itis.recipesjc.dao.ExtendedIngredientDao
import ru.itis.recipesjc.dao.RecipeDao
import ru.itis.recipesjc.model.entity.DetailRecipeEntity
import ru.itis.recipesjc.model.entity.ExtendedIngredientEntity
import ru.itis.recipesjc.model.entity.Recipe

@Database(
    entities = [
        Recipe::class,
        DetailRecipeEntity::class,
        ExtendedIngredientEntity::class
   ],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun detailRecipeDao(): DetailRecipeDao
    abstract fun extendedIngredientDao(): ExtendedIngredientDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipeDB.db"
                )
                    .build()
                    .also { INSTANCE = it }
            }
        }

        fun getRecipeDao(context: Context): RecipeDao {
            return getDatabase(context).recipeDao()
        }

        fun getDetailRecipeDao(context: Context): DetailRecipeDao {
            return getDatabase(context).detailRecipeDao()
        }

        fun getExtendedIngredientDao(context: Context): ExtendedIngredientDao {
            return getDatabase(context).extendedIngredientDao()
        }
    }
}