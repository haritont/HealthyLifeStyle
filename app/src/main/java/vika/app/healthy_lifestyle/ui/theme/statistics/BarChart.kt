package vika.app.healthy_lifestyle.ui.theme.statistics

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.axis.DataCategoryOptions
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import vika.app.healthy_lifestyle.calculations.DateToday
import kotlin.random.Random

@Composable
fun BarChartYCharts(data: List<Float>, labels: List<String>) {
    val list = arrayListOf<BarData>()
    for (index in data.indices) {
        val point = Point(
            index.toFloat(),
            data[index],
            description = data[index].toString()
        )

        list.add(
            BarData(
                point = point,
                color = Color(
                    Random.nextInt(256), Random.nextInt(256), Random.nextInt(256)
                ),
                description = data[index].toString()
            )
        )
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(20.dp)
        .steps(list.size - 1)
        .bottomPadding(40.dp)
        .startDrawPadding(20.dp)
        .axisLabelAngle(0f)
        .labelData { index -> DateToday().formatDateDDMM(labels[index]) }
        .build()

    val yAxisData = AxisData.Builder().build()

    val barChartData = BarChartData(
        chartData = list,
        xAxisData = xAxisData,
        yAxisData = yAxisData
    )

    BarChart(
        modifier = Modifier
            .height(400.dp)
            .padding(20.dp),
        barChartData = barChartData
    )
}
