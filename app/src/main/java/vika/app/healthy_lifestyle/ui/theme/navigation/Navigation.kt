package vika.app.healthy_lifestyle.ui.theme.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.FoodScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.MainScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.MoodScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.SportScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.StatisticsScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.main.HistoryScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.main.ProfileScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){
    val navController: NavHostController = rememberNavController()
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                actions = {
                    ImageButton(icon = R.drawable.login) {
                        navController.navigate(Screens.ProfileScreen.name)
                    }
                    ImageButton(icon = R.drawable.barcode) {
                        navController.navigate(Screens.BarcodeScannerScreen.name)
                    }
                    ImageButton(icon = R.drawable.history) {
                        navController.navigate(Screens.HistoryScreen.name)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                listItems.forEach{ navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any{it.route == navItem.route} == true,
                        onClick = {
                                  navController.navigate((navItem.route)){
                                      popUpTo(navController.graph.findStartDestination().id){
                                          saveState = true
                                      }
                                      launchSingleTop = true
                                      restoreState = true
                                  }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.icon),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    )
                }
            }
        }
    ){
        paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.MainScreen.name,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(route = Screens.MainScreen.name){
                MainScreen()
            }
            composable(route = Screens.FoodScreen.name){
                FoodScreen()
            }
            composable(route = Screens.SportScreen.name){
                SportScreen()
            }
            composable(route = Screens.MoodScreen.name){
                MoodScreen()
            }
            composable(route = Screens.StatisticsScreen.name){
                StatisticsScreen()
            }
            composable(route = Screens.ProfileScreen.name) {
                ProfileScreen()
            }
            composable(route = Screens.HistoryScreen.name) {
                HistoryScreen()
            }
        }
    }
}