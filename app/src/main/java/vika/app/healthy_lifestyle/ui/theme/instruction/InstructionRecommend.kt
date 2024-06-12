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
fun InstructionRecommend (
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit
){
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
                            text = "Как работают рекомендации?",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                        Text(
                            text = "Система подсчитывает, сколько кбжу вы употребили за прием пищи " +
                                    "и сравнивает это значение с целевым",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.rec_meal),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Если параметры кбжу превышены, то система предложит вам " +
                                    "измененный список продуктов, в котором кбжу будет сбалансирован",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.rec_list),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                        Text(
                            text = "Также при повышеном значении ккал система предложит упражнение, " +
                                    "которое поможет снизить это значение",
                            modifier = Modifier.padding(8.dp),
                            color = Black
                        )
                        Image(
                            painter = painterResource(id = R.drawable.rec_phys),
                            contentDescription = null,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}