package vika.app.healthy_lifestyle.ui.theme.general

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp

@Composable
fun ImageButton ( icon: Int, onClick: () -> Unit){
    Image(
        modifier = Modifier.clickable( onClick = onClick)
            .size(35.dp)
            .padding(5.dp),
        painter = painterResource(icon),
        contentDescription = null
    )
}