package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.sport.SportActivity
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.ui.theme.food.Header
import vika.app.healthy_lifestyle.ui.theme.food.LastAdded
import vika.app.healthy_lifestyle.ui.theme.general.Advice
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.list.ListElement
import vika.app.healthy_lifestyle.ui.theme.instruction.InstructionSport
import vika.app.healthy_lifestyle.ui.theme.sport.AddTraining
import vika.app.healthy_lifestyle.ui.theme.tracker.step.StepTracker


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun SportScreen () {
    val context = LocalContext.current

    val itemListPhysicalExercises = mutableListOf<Item>()
    val physicalExercises = SportActivity().getAll(context)
    if (physicalExercises != null) {
        for (physicalExercise in physicalExercises) {
            itemListPhysicalExercises.add(
                Item(
                    physicalExercise.name,
                    physicalExercise.type.type,
                    physicalExercise.favorite,
                    physicalExercise.exception,
                    if (physicalExercise.training) 3 else 2
                )
            )
        }
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

    var isShowingTips by remember { mutableStateOf(false) }

    Column {
        Header(isShowingTips) { isShowingTips = !isShowingTips }

        if (isShowingTips) {
            InstructionSport(
                isOpen = true,
                onOpenChange = { isOpen -> isShowingTips = isOpen }
            )
        }
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                if (selectListSports.size != 0) {
                    LastAdded(selectListSports) { item, title ->
                        selectListSports.remove(selectListSports.find { it.title == title })
                        SportActivity().deleteActivism(
                            context, item.title, item.value, DateToday().getToday()
                        )
                    }
                }

                StepTracker()
                Advice(value = SportActivity().getAdvice(context))
                var openDialogAddTraining by remember { mutableStateOf(false) }

                AddTraining(
                    isOpen = openDialogAddTraining,
                    onOpenChange = { openDialogAddTraining = it },
                    getAdd = { name, type ->
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

                ButtonBlue(text = LocalContext.current.getString(R.string.create_traning)) {
                    openDialogAddTraining = true
                }

                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(450.dp)
                ) {
                    ListElement(
                        itemList = filteredListPhysicalExercises,
                        add = { name, value, date, _ ->
                            SportActivity().add(
                                context,
                                name,
                                value,
                                date
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
                        textInDialog = LocalContext.current.getString(R.string.input_add_phys),
                        listOf(),
                        "",
                        1,
                        clickSearch = {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index = 4)
                            }
                        },
                        getAdd = { name, type ->
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
}