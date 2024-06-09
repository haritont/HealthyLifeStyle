package vika.app.healthy_lifestyle.base.data.database.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.main.TypeDao
import vika.app.healthy_lifestyle.base.data.entity.main.TypeEntity

@Database(entities = [TypeEntity::class], version = 1, exportSchema = false)
abstract class TypeDatabase : RoomDatabase() {
    abstract fun typeDao(): TypeDao

    companion object {
        @Volatile
        private var INSTANCE: TypeDatabase? = null

        fun getInstance(context: Context): TypeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TypeDatabase::class.java,
                    "Type.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}