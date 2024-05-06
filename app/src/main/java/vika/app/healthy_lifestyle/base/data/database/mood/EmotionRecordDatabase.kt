package vika.app.healthy_lifestyle.base.data.database.mood

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.mood.EmotionRecordDao
import vika.app.healthy_lifestyle.base.data.entity.mood.EmotionRecordEntity

@Database(entities = [EmotionRecordEntity::class], version = 1, exportSchema = false)
abstract class EmotionRecordDatabase : RoomDatabase() {
    abstract fun emotionRecordDao(): EmotionRecordDao

    companion object {
        @Volatile
        private var INSTANCE: EmotionRecordDatabase? = null

        fun getInstance(context: Context): EmotionRecordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmotionRecordDatabase::class.java,
                    "EmotionRecord.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}