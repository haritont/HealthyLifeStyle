package vika.app.healthy_lifestyle.base.data.dao.main

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vika.app.healthy_lifestyle.base.data.entity.main.NotificationEntity

@Dao
interface NotificationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationEntity)

    @Query("SELECT * FROM Notifications")
    suspend fun getAll(): List<NotificationEntity>?

    @Query("SELECT COUNT(*) FROM Notifications")
    suspend fun getRowCount(): Int

    @Query("UPDATE Notifications SET hour = :newHours, minute = :newMinutes WHERE text = :text")
    suspend fun updateHoursAndMinutes(text: String, newHours: Int, newMinutes: Int)

    @Query("SELECT hour FROM Notifications WHERE text = :text")
    suspend fun getHour(text: String): Int

    @Query("SELECT minute FROM Notifications WHERE text = :text")
    suspend fun getMinute(text: String): Int
}