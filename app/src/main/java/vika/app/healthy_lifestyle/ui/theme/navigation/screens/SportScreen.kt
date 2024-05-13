package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.activity.sport.SportActivity
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.general.Advice
import vika.app.healthy_lifestyle.ui.theme.general.SwitchButtons
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete
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

    val selectListSports = remember { mutableStateListOf<ItemText>() }

    var lastListSports = SportActivity().getLastActivism(context)
    lastListSports = lastListSports.reversed()

    for (activism in lastListSports) {
        selectListSports.add(ItemText(activism.name, activism.value))
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (selectListSports.size != 0) {
                Text(
                    text = "Последние добавленные",
                    modifier = Modifier.padding(8.dp),
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                LazyColumn(
                    modifier = Modifier
                        .height(150.dp)
                        .padding(8.dp)
                ) {
                    items(selectListSports) { item ->
                        key(item) {
                            ItemListDelete(
                                title = item.title,
                                value = item.value,
                                delete = { title ->
                                    selectListSports.remove(
                                        selectListSports.find { it.title == title }
                                    )
                                    SportActivity().deleteActivism(
                                        context,
                                        item.title,
                                        item.value,
                                        DateToday().getToday()
                                    )
                                }
                            )
                        }
                    }
                }
            }

            StepTracker()
            Advice(value = FoodActivity().getAdvice(context))

            val listStateRow = rememberLazyListState()
            val coroutineScopeRow = rememberCoroutineScope()
            SwitchButtons(
                textButton1 = "Упражнения",
                textButton2 = "Тренировки",
                clickButton1 = {
                    coroutineScopeRow.launch {
                        listStateRow.animateScrollToItem(index = 0)
                    }
                },
                clickButton2 = {
                    coroutineScopeRow.launch {
                        listStateRow.animateScrollToItem(index = 1)
                    }
                }
            )
            LazyRow(
                state = listStateRow
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(390.dp)
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
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index = 0)
                                }
                                selectListSports.add(
                                    0,
                                    ItemText(
                                        name,
                                        value
                                    )
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
                            2,
                            clickSearch = {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index = 4)
                                }
                            }
                        )
                    }

                    Spacer(Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .width(350.dp)
                            .height(390.dp)
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
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index = 0)
                                }
                                selectListSports.add(
                                    0,
                                    ItemText(
                                        name,
                                        value
                                    )
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
                            3,
                            clickSearch = {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(index = 4)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}