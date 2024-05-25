package vika.app.healthy_lifestyle.recommend

data class MealPlan(
    var target: Int? = null,
    var meal: Int? = null,
    var proteinMin: Int = 0,
    var proteinMax: Int = 0,
    var fatMin: Int = 0,
    var fatMax: Int = 0,
    var carbMin: Int = 0,
    var carbMax: Int = 0,
    var types: List<String> = listOf()
)
