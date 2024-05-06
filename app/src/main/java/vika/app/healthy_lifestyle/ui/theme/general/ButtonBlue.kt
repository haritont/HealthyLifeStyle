package vika.app.healthy_lifestyle.ui.theme.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Blue

@Composable
fun ButtonBlue(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .background(
                color = Blue,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = Black,
            fontWeight = FontWeight.Bold
        )
    }
}
