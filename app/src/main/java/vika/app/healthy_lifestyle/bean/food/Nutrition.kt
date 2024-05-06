package vika.app.healthy_lifestyle.bean.food

data class Nutrition(
    var id: Long = 0,
    var token: String = "",
    var name: String,
    var value: Double,
    var date: String,
    var meal: String
)
