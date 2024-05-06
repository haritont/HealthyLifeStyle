package vika.app.healthy_lifestyle.base.data.database.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.main.PersonalDataDao
import vika.app.healthy_lifestyle.base.data.entity.main.PersonalDataEntity

@Database(entities = [PersonalDataEntity::class], version = 1, exportSchema = false)
abstract class PersonalDataDatabase : RoomDatabase(){
    abstract fun personalDataDao(): PersonalDataDao

    companion object {
        @Volatile
        private var INSTANCE: PersonalDataDatabase? = null

        fun getInstance(context: Context): PersonalDataDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PersonalDataDatabase::class.java,
                    "PersonalData.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}