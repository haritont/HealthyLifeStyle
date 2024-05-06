package vika.app.healthy_lifestyle.base.data.database.main

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vika.app.healthy_lifestyle.base.data.dao.main.NotificationsDao
import vika.app.healthy_lifestyle.base.data.entity.main.NotificationEntity

@Database(entities = [NotificationEntity::class], version = 1, exportSchema = false)
abstract class NotificationsDatabase : RoomDatabase(){
    abstract fun notificationDao(): NotificationsDao

    companion object {
        @Volatile
        private var INSTANCE: NotificationsDatabase? = null

        fun getInstance(context: Context): NotificationsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotificationsDatabase::class.java,
                    "Notifications.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}