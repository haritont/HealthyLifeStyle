package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import vika.app.healthy_lifestyle.activity.sport.SportActivity
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.notification.scheduleNotification
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.general.Advice
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete
import vika.app.healthy_lifestyle.ui.theme.sport.AddTraining
import vika.app.healthy_lifestyle.ui.theme.tracker.step.StepTracker


@Composable
fun SportScreen () {
    val context = LocalContext.current

    val itemListPhysicalExercises = mutableListOf<Item>()
    val physicalExercises = SportActivity().getAll(context)
    for (physicalExercise in physicalExercises) {
        itemListPhysicalExercises.add(
            Item(
                physicalExercise.name,
                physicalExercise.type,
                physicalExercise.favorite,
                physicalExercise.exception,
                if (physicalExercise.training) 3 else 2
            )
        )
    }
    val filteredListPhysicalExercises by remember { mutableStateOf(itemListPhysicalExercises) }

    val selectListSports = remember { mutableStateListOf<ItemText>() }

    var lastListSports = SportActivity().getLastActivism(context)
    lastListSports = lastListSports.reversed()

    remember {
        lastListSports.forEach { activism ->
            if (selectListSports.none { it.title == activism.name }) {
                selectListSports.add(ItemText(activism.name, activism.value))
            }
        }
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
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
            Advice(value = SportActivity().getAdvice(context))
            var openDialogAddTraining by remember { mutableStateOf(false) }

            AddTraining(
                isOpen = openDialogAddTraining,
                onOpenChange = { openDialogAddTraining = it },
                getAdd = {name, type ->
                    filteredListPhysicalExercises.add(
                        Item(
                            name,
                            type,
                            favorite = false,
                            exception = false,
                            typeIs = 3
                        )
                    )
                }
            )

            ButtonBlue(text = "Создать тренировку") {
                openDialogAddTraining = true
            }

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .height(450.dp)
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
                        if (value > 30) {
                            scheduleNotification(
                                context,
                                "Попейте воды",
                                DateToday().getCurrentHour(),
                                DateToday().getCurrentMinute()
                            )
                        }
                    },
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
                    1,
                    clickSearch = {
                        coroutineScope.launch {
                            listState.animateScrollToItem(index = 4)
                        }
                    },
                    getAdd = {name, type ->
                        filteredListPhysicalExercises.add(
                            Item(
                                name,
                                type,
                                favorite = false,
                                exception = false,
                                typeIs = 2
                            )
                        )
                    }
                )
            }
        }
    }
}