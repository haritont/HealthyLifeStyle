package vika.app.healthy_lifestyle.base.data.database.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import vika.app.healthy_lifestyle.base.data.dao.main.RecordDao
import vika.app.healthy_lifestyle.base.data.entity.main.RecordEntity

@Database(entities = [RecordEntity::class], version = 2, exportSchema = false)
abstract class RecordDatabase : RoomDatabase() {

    abstract fun recordDao(): RecordDao

    companion object {
        private const val DATABASE_NAME = "Record.db"

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Record ADD COLUMN targetWater REAL NOT NULL DEFAULT 0.0")
                database.execSQL("ALTER TABLE Record ADD COLUMN progressWater REAL NOT NULL DEFAULT 0.0")
            }
        }

        @Volatile
        private var INSTANCE: RecordDatabase? = null

        fun getInstance(context: Context): RecordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecordDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
