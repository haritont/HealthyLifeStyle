package vika.app.healthy_lifestyle.base.data.entity.food

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Recipe")
class RecipeEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var idDish: Long = 0,
    var idIngredient: Long = 0,
    var valueIngredient: Double = 0.0
)