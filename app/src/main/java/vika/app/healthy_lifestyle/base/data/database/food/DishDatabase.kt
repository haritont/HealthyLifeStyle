package vika.app.healthy_lifestyle.base.data.database.food

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.food.DishDao
import vika.app.healthy_lifestyle.base.data.entity.food.DishEntity

@Database(entities = [DishEntity::class], version = 1, exportSchema = false)
abstract class DishDatabase : RoomDatabase() {
    abstract fun dishDao(): DishDao

    companion object {
        @Volatile
        private var INSTANCE: DishDatabase? = null

        fun getInstance(context: Context): DishDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DishDatabase::class.java,
                    "Dish.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}