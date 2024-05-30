package vika.app.healthy_lifestyle.base.data.entity.mood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "EmotionRecord")
class EmotionRecordEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var date: String = "",
    var idEmotion: Long = 0
)