package vika.app.healthy_lifestyle.base.data.database.sport

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.sport.TrainingDao
import vika.app.healthy_lifestyle.base.data.entity.sport.TrainingEntity

@Database(entities = [TrainingEntity::class], version = 1, exportSchema = false)
abstract class TrainingDatabase : RoomDatabase(){
    abstract fun trainingDao(): TrainingDao

    companion object {
        @Volatile
        private var INSTANCE: TrainingDatabase? = null

        fun getInstance(context: Context): TrainingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TrainingDatabase::class.java,
                    "Training.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}