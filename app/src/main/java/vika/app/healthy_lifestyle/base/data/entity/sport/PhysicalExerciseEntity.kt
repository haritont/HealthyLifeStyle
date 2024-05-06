package vika.app.healthy_lifestyle.base.data.entity.sport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PhysicalExercise")
class PhysicalExerciseEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var met: Double = 0.0,
    var type: String = "",
    var training: Boolean = false,
    var favorite: Boolean = false,
    var exception: Boolean = false
)