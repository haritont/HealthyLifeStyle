package vika.app.healthy_lifestyle.bean.food

class Dish (
    var id: Long = 0,
    var name: String,
    var kilocalories: Double,
    var proteins: Double,
    var fats: Double,
    var carbohydrates: Double,
    var favorite: Boolean = false,
    var exception: Boolean = false,
    var type: String = ""
)