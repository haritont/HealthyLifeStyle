package vika.app.healthy_lifestyle.base.data.mapper.main

import vika.app.healthy_lifestyle.base.data.entity.main.NotificationEntity
import vika.app.healthy_lifestyle.bean.main.Notification

interface NotificationMapper {
    fun toNotificationEntity(notification: Notification): NotificationEntity
    fun toNotification(notificationEntity: NotificationEntity): Notification
    fun toNotificationList(notificationEntities: List<NotificationEntity>): List<Notification>
}

class DefaultNotificationMapper: NotificationMapper {
    override fun toNotificationEntity(notification: Notification): NotificationEntity {
       return NotificationEntity(
           notification.id,
           notification.text,
           notification.hour,
           notification.minute
       )
    }

    override fun toNotification(notificationEntity: NotificationEntity): Notification {
        return Notification(
            notificationEntity.id,
            notificationEntity.text,
            notificationEntity.hour,
            notificationEntity.minute
        )
    }

    override fun toNotificationList(notificationEntities: List<NotificationEntity>) : List<Notification>{
        val notifications = mutableListOf<Notification>()
        for (notificationEntity in notificationEntities){
            notifications.add(toNotification(notificationEntity))
        }
        return notifications
    }
}