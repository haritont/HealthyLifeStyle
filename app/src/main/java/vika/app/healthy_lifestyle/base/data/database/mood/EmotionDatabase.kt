package vika.app.healthy_lifestyle.base.data.database.mood

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.mood.EmotionDao
import vika.app.healthy_lifestyle.base.data.entity.mood.EmotionEntity

@Database(entities = [EmotionEntity::class], version = 1, exportSchema = false)
abstract class EmotionDatabase : RoomDatabase() {
    abstract fun emotionDao(): EmotionDao

    companion object {
        @Volatile
        private var INSTANCE: EmotionDatabase? = null

        fun getInstance(context: Context): EmotionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmotionDatabase::class.java,
                    "Emotion.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}