package vika.app.healthy_lifestyle.base.data.database.sport

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.sport.ActivismDao
import vika.app.healthy_lifestyle.base.data.entity.sport.ActivismEntity

@Database(entities = [ActivismEntity::class], version = 1, exportSchema = false)
abstract class ActivismDatabase : RoomDatabase(){
    abstract fun activismDao(): ActivismDao

    companion object {
        @Volatile
        private var INSTANCE: ActivismDatabase? = null

        fun getInstance(context: Context): ActivismDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActivismDatabase::class.java,
                    "Activism.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}