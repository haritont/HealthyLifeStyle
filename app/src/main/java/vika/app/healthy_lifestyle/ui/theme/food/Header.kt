package vika.app.healthy_lifestyle.ui.theme.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton

@Composable
fun Header(isShowingTips: Boolean, onIconClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
    ) {
        ImageButton(icon = R.drawable.question, onClick = onIconClick)
    }
}