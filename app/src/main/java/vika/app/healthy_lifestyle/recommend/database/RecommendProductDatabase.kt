package vika.app.healthy_lifestyle.recommend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecommendProductEntity::class], version = 1, exportSchema = false)
abstract class RecommendProductDatabase : RoomDatabase(){
    abstract fun recommendProductDao(): RecommendProductDao

    companion object {
        @Volatile
        private var INSTANCE: RecommendProductDatabase? = null

        fun getInstance(context: Context): RecommendProductDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecommendProductDatabase::class.java,
                    "RecommendProduct.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}