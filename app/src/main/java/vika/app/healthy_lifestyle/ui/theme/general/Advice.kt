package vika.app.healthy_lifestyle.ui.theme.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.BlueUltraLight
import vika.app.healthy_lifestyle.ui.theme.app.White

@Composable
fun Advice(
    value: String
){
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .padding(3.dp)
            .width(300.dp)
            .shadow(1.dp, RoundedCornerShape(10.dp))
            .border(
                width = 4.dp,
                color = BlueUltraLight,
                shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = White
            )
            .clickable { isExpanded = !isExpanded }
    ) {
        Image(
            painter = painterResource(R.drawable.lamp),
            contentDescription = null,
            modifier = Modifier
                .padding(15.dp)
                .size(25.dp)
        )
        Text(
            text = if (value == "") LocalContext.current.getString(R.string.default_advice) else "Совет ⬇\uFE0F\n".plus(value),
            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
            modifier = Modifier
                .padding(15.dp)
        )
    }
}