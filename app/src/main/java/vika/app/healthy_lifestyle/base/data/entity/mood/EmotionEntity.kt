package vika.app.healthy_lifestyle.base.data.entity.mood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Emotion")
class EmotionEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var type: Int = 0
)