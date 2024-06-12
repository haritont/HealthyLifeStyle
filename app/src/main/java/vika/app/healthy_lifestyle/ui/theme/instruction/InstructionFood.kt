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
fun InstructionFood(
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
                            text = "Как добавить продукт?",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Введите название продукта, который хотите добавить, в строку поиска",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.instr_search),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Выберите нужный из списка и кликните по нему, чтобы добавить",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.instr_item),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Если продукта не оказалось в списке, нажмите на кнопку рядом " +
                                    "со строкой поиска.",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.instr_search),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Если вы не знаете кбжу продукта, но знаете его состав и граммовки, " +
                                    "то вы может создать свой рецепт" +
                                    " и добавить его в список",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.recipe),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Если знаете примерное употребленное вами кбжу, " +
                                    "но вам не хочется вводить все продукты, воспользуйтесь следующей кнопкой",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.fast),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )

                        Text(
                            text = "Как исключить продукт из рекомендаций и советов?",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Найдите продукт и нажмите на кнопку",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ex),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )

                        Text(
                            text = "Как добавить продукт в рекомендации и советы?",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Найдите продукт и нажмите на кнопку",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.fav),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}