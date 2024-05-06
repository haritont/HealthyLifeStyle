package vika.app.healthy_lifestyle.ui.theme.navigation

import vika.app.healthy_lifestyle.R

data class NavigationItem (
    val label: String,
    val icon: Int,
    val route: String
)

val listItems = listOf(
    NavigationItem(
        label = "Home",
        icon = R.drawable.main,
        route = Screens.MainScreen.name
    ),
    NavigationItem(
        label = "Food",
        icon = R.drawable.food,
        route = Screens.FoodScreen.name
    ),
    NavigationItem(
        label = "Sport",
        icon = R.drawable.sport,
        route = Screens.SportScreen.name
    ),
    NavigationItem(
        label = "Mood",
        icon = R.drawable.mood,
        route = Screens.MoodScreen.name
    ),
    NavigationItem(
        label = "Statistics",
        icon = R.drawable.statistics,
        route = Screens.StatisticsScreen.name
    )
)