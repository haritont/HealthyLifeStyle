package vika.app.healthy_lifestyle.ui.theme.instruction

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.ui.theme.app.Black

@Composable
fun InstructionSport(
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit
) {
    var openDialog by remember { mutableStateOf(isOpen) }

    if (isOpen != openDialog) {
        openDialog = isOpen
    }

    DisposableEffect(isOpen) {
        onOpenChange(openDialog)
        onDispose { }
    }

    if (openDialog) {
        Dialog(
            onDismissRequest = {
                openDialog = !openDialog
                onOpenChange(openDialog)
            }
        ) {
            Card(
                modifier = Modifier.padding(8.dp)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Text(
                            text = "Как пользоваться шагомером?",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Вкючите шагомер для использования в фоновом режиме",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.step_play),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Выключите шагомер для отключения использования в фоновом режиме",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.step_stop),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "При отключении использования в фоновом режиме данные будут " +
                                    "обновляться только при открытом приложении",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                    }
                }
            }
        }
    }
}