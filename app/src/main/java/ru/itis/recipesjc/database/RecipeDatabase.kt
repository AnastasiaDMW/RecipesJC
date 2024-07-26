package ru.itis.recipesjc.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.itis.recipesjc.dao.RecipeDao
import ru.itis.recipesjc.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

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
    }
}