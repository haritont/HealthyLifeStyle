package vika.app.healthy_lifestyle.base.data.database.food

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.food.IngredientDao
import vika.app.healthy_lifestyle.base.data.entity.food.IngredientEntity

@Database(entities = [IngredientEntity::class], version = 2, exportSchema = false)
abstract class IngredientDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao

    companion object {
        @Volatile
        private var INSTANCE: IngredientDatabase? = null

        fun getInstance(context: Context): IngredientDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IngredientDatabase::class.java,
                    "Ingredient.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
