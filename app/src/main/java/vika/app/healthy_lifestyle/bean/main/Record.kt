package vika.app.healthy_lifestyle.bean.main

class Record (
    var id: Long = 0,
    var date:String,
    var targetKilocalories: Double = 0.0,
    var targetProteins: Double = 0.0,
    var targetFats: Double = 0.0,
    var targetCarbohydrates: Double = 0.0,
    var targetWater: Double = 0.0,
    var targetSteps: Int = 10000,
    var progressKilocalories: Double = 0.0,
    var progressProteins: Double = 0.0,
    var progressFats: Double = 0.0,
    var progressCarbohydrates: Double = 0.0,
    var progressWater: Int = 0,
    var progressSteps: Int = 0,
    var burnedKilocalories: Double = 0.0
)