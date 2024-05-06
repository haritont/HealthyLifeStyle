package vika.app.healthy_lifestyle.base.data.entity.mood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HabitRecord")
class HabitRecordEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var idHabit: String = "",
    var dateStart: String = "",
    var dateEnd: String = ""
)