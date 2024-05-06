package vika.app.healthy_lifestyle.ui.theme.general.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.app.White
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun ItemListDelete(
    title: String,
    value: Double,
    delete: (String) -> Unit
){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .shadow(3.dp, RoundedCornerShape(5.dp))
            .background(
                color = White,
                shape = RoundedCornerShape(5.dp)
            )
            .border(
                width = 1.dp,
                color = BlueUltraLight,
                shape = RoundedCornerShape(5.dp)
            )
            .width(300.dp)
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(5f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    maxLines = 3
                )
                Text(
                    text = value.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    maxLines = 3
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                ImageButton(icon = R.drawable.delete) {
                    delete(title)
                }
            }
        }
    }
}