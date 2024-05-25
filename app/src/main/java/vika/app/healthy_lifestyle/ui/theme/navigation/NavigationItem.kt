package vika.app.healthy_lifestyle.ui.theme.navigation

import vika.app.healthy_lifestyle.R

data class NavigationItem (
    val label: String,
    val icon: Int,
    val route: String
)

val listItemsBottom = listOf(
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

val listItemsTop = listOf(
    NavigationItem(
        label = "Profile",
        icon = R.drawable.login,
        route = Screens.ProfileScreen.name
    ),
    NavigationItem(
        label = "History",
        icon = R.drawable.history,
        route = Screens.HistoryScreen.name
    ),
    NavigationItem(
        label = "BarcodeScannerScreen",
        icon = R.drawable.barcode,
        route = Screens.BarcodeScannerScreen.name
    ),
    NavigationItem(
        label = "Recommend",
        icon = R.drawable.mood,
        route = Screens.RecommendScreen.name
    )
)