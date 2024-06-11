package vika.app.healthy_lifestyle.ui.theme.statistics

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import vika.app.healthy_lifestyle.calculations.DateToday

@Composable
fun BarChartMPA(values: List<Float>, dates: List<String>, unit: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                BarChart(context).apply {
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.granularity = 1f
                    xAxis.setDrawGridLines(false)
                    xAxis.valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(value: Float): String {
                            val index = value.toInt()
                            return if (index >= 0 && index <= dates.size) {
                                DateToday().formatDateDDMM(dates[index - 1])
                            } else {
                                ""
                            }
                        }
                    }
                    axisRight.isEnabled = false

                    val entries = values.mapIndexed { index, value ->
                        BarEntry((index + 1).toFloat(), value)
                    }

                    val dataSet = BarDataSet(entries, "").apply {
                        color = Color.argb(200, 73, 204, 253)
                        valueTextColor = Color.BLACK
                        valueTextSize = 15f
                        valueFormatter = object : ValueFormatter() {
                            override fun getBarLabel(entry: BarEntry?): String {
                                return entry?.y?.let { String.format("%.1f", it).plus(unit) } ?: ""
                            }
                        }
                    }

                    val barData = BarData(dataSet)
                    barData.barWidth = 0.9f

                    data = barData
                    description.isEnabled = false
                    legend.isEnabled = false

                    animateY(1000)

                    isDragEnabled = true
                    setScaleEnabled(false)

                    setVisibleXRangeMaximum(7f)
                    setVisibleXRangeMinimum(5f)

                    invalidate()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
