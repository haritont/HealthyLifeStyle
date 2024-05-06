package vika.app.healthy_lifestyle.base.data.entity.sport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Activism")
class ActivismEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var value: Double = 0.0,
    var date: String = ""
)