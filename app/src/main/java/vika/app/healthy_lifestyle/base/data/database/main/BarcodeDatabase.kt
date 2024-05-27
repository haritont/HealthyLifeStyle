package vika.app.healthy_lifestyle.base.data.database.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.main.BarcodeDao
import vika.app.healthy_lifestyle.base.data.entity.main.BarcodeEntity

@Database(entities = [BarcodeEntity::class], version = 1, exportSchema = false)
abstract class BarcodeDatabase  : RoomDatabase(){
    abstract fun barcodeDao(): BarcodeDao

    companion object {
        @Volatile
        private var INSTANCE: BarcodeDatabase? = null

        fun getInstance(context: Context): BarcodeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BarcodeDatabase::class.java,
                    "Barcode.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}