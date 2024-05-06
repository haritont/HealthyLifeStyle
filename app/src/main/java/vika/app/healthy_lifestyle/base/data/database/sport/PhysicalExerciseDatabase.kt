package vika.app.healthy_lifestyle.base.data.database.sport

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.sport.PhysicalExerciseDao
import vika.app.healthy_lifestyle.base.data.entity.sport.PhysicalExerciseEntity

@Database(entities = [PhysicalExerciseEntity::class], version = 1, exportSchema = false)
abstract class PhysicalExerciseDatabase : RoomDatabase(){
    abstract fun physicalExerciseDao(): PhysicalExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: PhysicalExerciseDatabase? = null

        fun getInstance(context: Context): PhysicalExerciseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhysicalExerciseDatabase::class.java,
                    "PhysicalExercise.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}