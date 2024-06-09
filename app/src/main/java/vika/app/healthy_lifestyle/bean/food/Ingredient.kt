package vika.app.healthy_lifestyle.bean.food

import vika.app.healthy_lifestyle.bean.main.Type


data class Ingredient(
    var id: Long = 0,
    var name: String,
    var kilocalories: Double,
    var proteins: Double,
    var fats: Double,
    var carbohydrates: Double,
    var favorite: Boolean = false,
    var exception: Boolean = false,
    var type: Type,
    var isDish: Boolean = false
)