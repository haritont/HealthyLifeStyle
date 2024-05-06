package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.main.HistoryActivity
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.general.DatePickerWithDialog
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemList
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete

@Composable
fun HistoryScreen() {
    val context = LocalContext.current

    var today = DateToday().getToday()
    val currentDate by remember { mutableStateOf(today)}
    var historyList by remember { mutableStateOf(HistoryActivity().getHistory(context, currentDate))}

    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ImageButton(icon = R.drawable.left) {
                    today = DateToday().subtractDay(today)
                    historyList = HistoryActivity().getHistory(context, today)
                }
                DatePickerWithDialog(
                    currentDate = currentDate,
                    getCurrentTime = { currentTime ->
                        today = currentTime
                    }
                )
                ImageButton(icon = R.drawable.right) {
                    today = DateToday().addDay(today)
                    historyList = HistoryActivity().getHistory(context, today)
                }
            }

            LazyColumn(
                modifier = Modifier.height(300.dp)
            ) {
                items(historyList) { item ->
                    key(item.name) {
                        ItemListDelete(
                            title = item.name,
                            value = item.value,
                            delete = { title ->
                                historyList.drop(
                                    historyList.indexOf(historyList.find { it.name == title })
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}