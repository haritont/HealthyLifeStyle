package vika.app.healthy_lifestyle.ui.theme.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.BlueLight
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.FoodScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.MainScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.MoodScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.SportScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.StatisticsScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.main.BarcodeScannerScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.main.HistoryScreen
import vika.app.healthy_lifestyle.ui.theme.navigation.screens.main.ProfileScreen

@Composable
fun Navigation(){
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold (
        topBar = {
            NavigationBar (
                contentColor = BlueLight
            ){
                listItemsTop.forEach{ navItem ->
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
        },
        bottomBar = {
            NavigationBar (
                contentColor = Blue
            ){
                listItemsBottom.forEach{ navItem ->
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
            composable(route = Screens.BarcodeScannerScreen.name){
                BarcodeScannerScreen()
            }
        }
    }
}