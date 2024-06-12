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
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.ui.theme.statistics.BarChartMPA
import vika.app.healthy_lifestyle.ui.theme.statistics.Dropdown


@Composable
fun StatisticsScreen() {
    var selectedOption by remember { mutableStateOf("Ккал") }
    val labels = DateToday().getMonth()
    var unit by remember { mutableStateOf("ккал") }

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
                    "Ккал" -> {
                        unit = "ккал"
                        StatisticsActivity().getKilocalories(labels)
                    }

                    "Вода" -> {
                        unit = "мл"
                        StatisticsActivity().getWaters(labels)
                    }

                    "Белки" -> {
                        unit = "гр"
                        StatisticsActivity().getProteins(labels)
                    }

                    "Жиры" -> {
                        unit = "гр"
                        StatisticsActivity().getFats(labels)
                    }

                    "Углеводы" -> {
                        unit = "гр"
                        StatisticsActivity().getCarbohydrates(labels)
                    }

                    "Спорт" -> {
                        unit = "ккал"
                        StatisticsActivity().getActivism(labels)
                    }

                    "Вес" -> {
                        unit = "кг"
                        StatisticsActivity().getWeights(labels)
                    }

                    "Сон" -> {
                        unit = "ч"
                        StatisticsActivity().getDreams(labels)
                    }

                    else -> {
                        unit = ""
                        mutableListOf()
                    }
                }
            }
        )

        key(data.joinToString(",")) {
            BarChartMPA(values = data, dates = labels, unit = unit)
        }
    }
}