package vika.app.healthy_lifestyle.base.data.repository.main

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.main.NotificationsDao
import vika.app.healthy_lifestyle.base.data.database.main.NotificationsDatabase
import vika.app.healthy_lifestyle.base.data.mapper.main.DefaultNotificationMapper
import vika.app.healthy_lifestyle.base.data.mapper.main.NotificationMapper
import vika.app.healthy_lifestyle.bean.main.Notification

class NotificationsRepository(context: Context) {
    private val notificationsDao: NotificationsDao
    private val notificationsDatabase: NotificationsDatabase = NotificationsDatabase.getInstance(context)
    private val notificationsMapper: NotificationMapper

    init {
        notificationsDao = notificationsDatabase.notificationDao()
        notificationsMapper = DefaultNotificationMapper()
    }

    fun insertNotifications(notification: Notification) = runBlocking{
        notificationsDao.insert(notificationsMapper.toNotificationEntity(notification))
    }

    fun getAllNotifications(): List<Notification> = runBlocking{
        notificationsMapper.toNotificationList(notificationsDao.getAll())
    }

    fun getNotificationsRowCount(): Int = runBlocking{
        notificationsDao.getRowCount()
    }

    fun updateHoursAndMinutesNotifications(text: String, newHours: Int, newMinutes: Int) = runBlocking{
        notificationsDao.updateHoursAndMinutes(text, newHours, newMinutes)
    }

    fun getNotificationsHour(text: String): Int = runBlocking{
        notificationsDao.getHour(text)
    }

    fun getNotificationsMinute(text: String): Int = runBlocking{
        notificationsDao.getMinute(text)
    }
}