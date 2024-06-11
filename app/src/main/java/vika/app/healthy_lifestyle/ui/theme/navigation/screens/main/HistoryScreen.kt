package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.activity.main.HistoryActivity
import vika.app.healthy_lifestyle.bean.main.History
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.ui.theme.general.DatePickerWithDialog
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListHistory

@Composable
fun HistoryScreen() {
    val context = LocalContext.current

    var currentDate by remember { mutableStateOf(DateToday().getToday()) }
    var historyList by remember {
        mutableStateOf(
            HistoryActivity().getHistory(
                context,
                currentDate
            )
        )
    }

    val selectListProduct = remember { mutableStateListOf<History>() }
    remember {
        historyList.forEach { nutrition ->
            if (selectListProduct.none { it.name == nutrition.name && it.value == nutrition.value}) {
                selectListProduct.add(nutrition)
            }
        }
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            DatePickerWithDialog(
                currentDate = currentDate,
                getCurrentTime = { currentTime ->
                    currentDate = currentTime
                    historyList = HistoryActivity().getHistory(context, currentDate)
                    selectListProduct.clear()
                    historyList.forEach { nutrition ->
                        if (selectListProduct.none { it.name == nutrition.name }) {
                            selectListProduct.add(nutrition)
                        }
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier.padding(8.dp).fillMaxSize()
        ) {
            items(selectListProduct) { item ->
                key(item.name) {
                    ItemListHistory(
                        title = item.name,
                        value = item.value,
                        type = item.type,
                        delete = { title ->
                            selectListProduct.remove(
                                selectListProduct.find { it.name == title }
                            )
                            HistoryActivity().deleteHistoryItem(
                                context,
                                currentDate,
                                item.name,
                                item.value,
                                item.type
                            )
                        }
                    )
                }
            }
        }
    }
}