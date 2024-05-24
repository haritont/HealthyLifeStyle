package vika.app.healthy_lifestyle.base.data.database.food

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.food.RecipeDao
import vika.app.healthy_lifestyle.base.data.entity.food.IngredientEntity
import vika.app.healthy_lifestyle.base.data.entity.food.RecipeEntity

@Database(entities = [IngredientEntity::class, RecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "Recipe.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}