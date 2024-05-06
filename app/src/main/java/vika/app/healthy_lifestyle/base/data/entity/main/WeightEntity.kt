package vika.app.healthy_lifestyle.base.data.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weight")
class WeightEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var date: String = "",
    var value: Double = 0.0
)