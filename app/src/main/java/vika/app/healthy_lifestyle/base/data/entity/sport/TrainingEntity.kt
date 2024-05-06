package vika.app.healthy_lifestyle.base.data.entity.sport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Training")
class TrainingEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var idName: Long,
    var idPhysicalExercise: Long,
    var valuePhysicalExercise: Double
)