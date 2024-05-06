package vika.app.healthy_lifestyle.ui.theme.navigation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import vika.app.healthy_lifestyle.activity.statistics.StatisticsActivity
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.statistics.BarChartYCharts
import vika.app.healthy_lifestyle.ui.theme.statistics.Dropdown


@Composable
fun StatisticsScreen() {
    var selectedOption by remember { mutableStateOf("Ккал") }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Dropdown(
            selectedOption = selectedOption,
            onOptionSelected = { option ->
                selectedOption = option
            }
        )

        val labels = DateToday().getWeek()

        var data = mutableListOf<Float>()
        when (selectedOption) {
            "Ккал" -> {
                data = StatisticsActivity().getKilocalories(labels)
            }
            "Вода" -> {
                data = StatisticsActivity().getWaters(labels)
            }
            "Белки" -> {
                data = StatisticsActivity().getProteins(labels)
            }
            "Жиры" -> {
                data = StatisticsActivity().getFats(labels)
            }
            "Углеводы" -> {
                data = StatisticsActivity().getCarbohydrates(labels)
            }
            "Спорт" -> {
                data = StatisticsActivity().getActivism(labels)
            }
        }

        BarChartYCharts(data = data, labels = labels)
    }
}