package vika.app.healthy_lifestyle.base.data.entity.food

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Dish")
class DishEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var kilocalories: Double = 0.0,
    var proteins: Double = 0.0,
    var fats: Double = 0.0,
    var carbohydrates: Double = 0.0,
    var favorite: Boolean = false,
    var exception: Boolean = false,
    var type: String = ""
)