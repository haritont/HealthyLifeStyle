package vika.app.healthy_lifestyle.base.data.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notifications")
class NotificationEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var text: String = "",
    var hour: Int = 0,
    var minute: Int = 0
)