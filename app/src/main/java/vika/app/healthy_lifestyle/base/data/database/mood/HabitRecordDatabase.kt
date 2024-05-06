package vika.app.healthy_lifestyle.base.data.database.mood

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.mood.HabitRecordDao
import vika.app.healthy_lifestyle.base.data.entity.mood.HabitRecordEntity

@Database(entities = [HabitRecordEntity::class], version = 1, exportSchema = false)
abstract class HabitRecordDatabase : RoomDatabase() {
    abstract fun habitRecordDao(): HabitRecordDao

    companion object {
        @Volatile
        private var INSTANCE: HabitRecordDatabase? = null

        fun getInstance(context: Context): HabitRecordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HabitRecordDatabase::class.java,
                    "HabitRecord.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}