package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import vika.app.healthy_lifestyle.activity.statistics.StatisticsActivity
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.statistics.BarChartMPA
import vika.app.healthy_lifestyle.ui.theme.statistics.Dropdown


@Composable
fun StatisticsScreen() {
    var selectedOption by remember { mutableStateOf("Ккал") }
    val labels = DateToday().getMonth()

    var data by remember { mutableStateOf(StatisticsActivity().getKilocalories(labels)) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Dropdown(
            selectedOption = selectedOption,
            onOptionSelected = { option ->
                selectedOption = option
                data = when (option) {
                    "Ккал" -> StatisticsActivity().getKilocalories(labels)
                    "Вода" -> StatisticsActivity().getWaters(labels)
                    "Белки" -> StatisticsActivity().getProteins(labels)
                    "Жиры" -> StatisticsActivity().getFats(labels)
                    "Углеводы" -> StatisticsActivity().getCarbohydrates(labels)
                    "Спорт" -> StatisticsActivity().getActivism(labels)
                    else -> {
                        mutableListOf()
                    }
                }
            }
        )

        key(data.joinToString(",")) {
            BarChartMPA(values = data, dates = labels)
        }
    }
}