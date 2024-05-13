package vika.app.healthy_lifestyle.base.data.entity.mood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Habit")
class HabitEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var product: String = "",
    var isPositive: Boolean = false
)