package vika.app.healthy_lifestyle.base.data.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Type")
class TypeEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val type: String = "",
    val isProduct: Boolean = false
)