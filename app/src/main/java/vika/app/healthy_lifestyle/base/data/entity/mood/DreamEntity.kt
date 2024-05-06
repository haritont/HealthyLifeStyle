package vika.app.healthy_lifestyle.base.data.entity.mood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dream")
class DreamEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var date: String = "",
    var hour: Int = 0,
    var minute: Int = 0
)