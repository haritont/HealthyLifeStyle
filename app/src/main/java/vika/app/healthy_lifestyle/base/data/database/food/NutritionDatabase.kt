package vika.app.healthy_lifestyle.base.data.database.food

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.food.NutritionDao
import vika.app.healthy_lifestyle.base.data.entity.food.NutritionEntity

@Database(entities = [NutritionEntity::class], version = 3, exportSchema = false)
abstract class NutritionDatabase : RoomDatabase(){
    abstract fun nutritionDao(): NutritionDao

    companion object {
        @Volatile
        private var INSTANCE: NutritionDatabase? = null

        fun getInstance(context: Context): NutritionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NutritionDatabase::class.java,
                    "Nutrition.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}