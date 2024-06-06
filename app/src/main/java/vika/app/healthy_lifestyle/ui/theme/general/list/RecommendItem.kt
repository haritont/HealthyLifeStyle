package vika.app.healthy_lifestyle.ui.theme.general.list

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.ui.theme.app.Black

@Composable
fun RecommendItem(
    title: String,
    color: Color,
    kilocalories: Double,
    proteins: Double,
    fats: Double,
    carbohydrates: Double,
    value: Double
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(5.dp)
            ),
        shape = RoundedCornerShape(16.dp),

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ){
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )
                    Text(
                        text = "%.1f".format(value).plus(" гр."),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }

            Box(
                modifier = Modifier.weight(2f)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "".plus("ккал: ").plus("%.1f".format(kilocalories))
                            .plus("; белки: ").plus("%.1f".format(proteins)),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    Text(
                        text = "".plus("жиры: ").plus("%.1f".format(fats))
                            .plus("; углеводы: ").plus("%.1f".format(carbohydrates)),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}