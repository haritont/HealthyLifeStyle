package vika.app.healthy_lifestyle.base.data.entity.main

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PersonalData")
class PersonalDataEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val genderId: Int = 1,
    val height: Double = 0.0,
    val weight: Double = 0.0,
    val birthDate: String = "",
    val activityRate: Double = 0.0,
    val name: String = "",
    val target: Int = 1 //0 - набрать, 1 - поддержка, 2 - похудеть
)
