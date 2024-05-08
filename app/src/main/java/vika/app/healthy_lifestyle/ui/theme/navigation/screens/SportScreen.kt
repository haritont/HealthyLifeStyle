package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.activity.sport.SportActivity
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.ui.theme.general.Advice
import vika.app.healthy_lifestyle.ui.theme.tracker.step.StepTracker


@Composable
fun SportScreen (){
    val context = LocalContext.current

    val itemListPhysicalExercises = mutableListOf<Item>()
    val physicalExercises = SportActivity().getAllPhysicalExercises(context)
    for (physicalExercise in physicalExercises) {
        itemListPhysicalExercises.add(
            Item(
                physicalExercise.name,
                physicalExercise.type,
                physicalExercise.favorite,
                physicalExercise.exception
            )
        )
    }
    val filteredListPhysicalExercises by remember { mutableStateOf(itemListPhysicalExercises) }

    var searchKey by remember { mutableStateOf(0) }

    val itemListTrainings = mutableListOf<Item>()
    val trainings = SportActivity().getAllTrainings(context)
    for (training in trainings) {
        itemListTrainings.add(
            Item(
                training.name,
                training.type,
                training.favorite,
                training.exception
            )
        )
    }
    val filteredListTrainings by remember { mutableStateOf(itemListTrainings) }

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            StepTracker()
            Advice(value = FoodActivity().getAdvice())

            LazyRow {
                item {
                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(500.dp)
                    ) {
                        vika.app.healthy_lifestyle.ui.theme.general.list.List(
                            itemList = filteredListPhysicalExercises,
                            add = { name, value, date, option ->
                                SportActivity().add(
                                    context,
                                    name,
                                    value,
                                    date,
                                    option
                                )
                            },
                            2,
                            updateException = { name, exception ->
                                SportActivity().updateException(
                                    context,
                                    name,
                                    exception
                                )
                            },
                            updateFavorite = { name, favorite ->
                                SportActivity().updateFavorite(
                                    context,
                                    name,
                                    favorite
                                )
                            },
                            textInDialog = "Сколько вы выполняли это упражнение? (мин)",
                            listOf(),
                            "",
                            2
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(500.dp)
                    ) {
                        vika.app.healthy_lifestyle.ui.theme.general.list.List(
                            itemList = filteredListTrainings,
                            add = { name, value, date, option ->
                                SportActivity().add(
                                    context,
                                    name,
                                    value,
                                    date,
                                    option
                                )
                            },
                           3,
                            updateException = { name, exception ->
                                SportActivity().updateException(
                                    context,
                                    name,
                                    exception
                                )
                            },
                            updateFavorite = { name, favorite ->
                                SportActivity().updateFavorite(
                                    context,
                                    name,
                                    favorite
                                )
                            },
                            textInDialog = "Сколько вы выполняли эту тренировку? (мин)",
                            listOf(),
                            "",
                            3
                        )
                    }
                }
            }
        }
    }
}