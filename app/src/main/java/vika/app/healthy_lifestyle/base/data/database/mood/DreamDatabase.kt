package vika.app.healthy_lifestyle.base.data.database.mood

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.mood.DreamDao
import vika.app.healthy_lifestyle.base.data.entity.mood.DreamEntity

@Database(entities = [DreamEntity::class], version = 1, exportSchema = false)
abstract class DreamDatabase : RoomDatabase() {
    abstract fun dreamDao(): DreamDao

    companion object {
        @Volatile
        private var INSTANCE: DreamDatabase? = null

        fun getInstance(context: Context): DreamDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DreamDatabase::class.java,
                    "Dream.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}