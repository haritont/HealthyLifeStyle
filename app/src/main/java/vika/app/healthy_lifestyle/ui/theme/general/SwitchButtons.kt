package vika.app.healthy_lifestyle.ui.theme.general

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.White

@Composable
fun SwitchButtons(
    textButton1: String,
    textButton2: String,
    clickButton1: () -> Unit,
    clickButton2: () -> Unit
) {
    var selectColor1 by remember { mutableStateOf(Blue) }
    var selectColor2 by remember { mutableStateOf(White) }

    Row {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = selectColor1,
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable {
                    selectColor1 = Blue
                    selectColor2 = White
                    clickButton1()
                }
        ) {
            Text(
                text = textButton1,
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
            )
        }

        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = selectColor2,
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable {
                    selectColor2 = Blue
                    selectColor1 = White
                    clickButton2()
                }
        ) {
            Text(
                text = textButton2,
                style = MaterialTheme.typography.bodyMedium,
                color = Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(10.dp)
            )
        }
    }
}