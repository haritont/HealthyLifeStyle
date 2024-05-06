package vika.app.healthy_lifestyle.base.data.database.mood

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.mood.HabitDao
import vika.app.healthy_lifestyle.base.data.entity.mood.HabitEntity

@Database(entities = [HabitEntity::class], version = 1, exportSchema = false)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    companion object {
        @Volatile
        private var INSTANCE: HabitDatabase? = null

        fun getInstance(context: Context): HabitDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitDatabase::class.java,
                    "Habit.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}