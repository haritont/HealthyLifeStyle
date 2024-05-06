package vika.app.healthy_lifestyle.base.data.database.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.main.WeightDao
import vika.app.healthy_lifestyle.base.data.entity.main.WeightEntity

@Database(entities = [WeightEntity::class], version = 1, exportSchema = false)
abstract class WeightDatabase : RoomDatabase() {
    abstract fun weightDao(): WeightDao

    companion object {
        @Volatile
        private var INSTANCE: WeightDatabase? = null

        fun getInstance(context: Context): WeightDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeightDatabase::class.java,
                    "Weight.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}