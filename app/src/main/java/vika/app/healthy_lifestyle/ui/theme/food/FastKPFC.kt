package vika.app.healthy_lifestyle.ui.theme.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.calculations.DateToday
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.general.ButtonBlue
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue

@Composable
fun FastKPFC() {
    val context = LocalContext.current

    var openDialog  by remember { mutableStateOf(false) }

    ButtonBlue(text = LocalContext.current.getString(R.string.fast_kpfc)) {
        openDialog = true
    }

    if (openDialog){
        Dialog(
            onDismissRequest = {  openDialog = !openDialog }
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val kilocaloriesState = remember { mutableStateOf("") }
                    val proteinsState = remember { mutableStateOf("") }
                    val fatsState = remember { mutableStateOf("") }
                    val carbohydratesState = remember { mutableStateOf("") }

                    Text(
                        text = LocalContext.current.getString(R.string.fast_kpfc),
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextFieldBlue(
                                value = kilocaloriesState.value,
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.kilocalories),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onValueChange = { newLogin -> kilocaloriesState.value = newLogin },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                leadingIcon = {
                                    Image(
                                        painterResource(R.drawable.kilocalories),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )
                                }
                            )
                        }
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextFieldBlue(
                                value = proteinsState.value,
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.proteins),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onValueChange = { newLogin -> proteinsState.value = newLogin },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                leadingIcon = {
                                    Image(
                                        painterResource(R.drawable.proteins),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )
                                }
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextFieldBlue(
                                value = fatsState.value,
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.fats),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onValueChange = { newLogin -> fatsState.value = newLogin },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                leadingIcon = {
                                    Image(
                                        painterResource(R.drawable.fats),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )
                                }
                            )
                        }

                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            TextFieldBlue(
                                value = carbohydratesState.value,
                                label = {
                                    Text(
                                        LocalContext.current.getString(R.string.carbohydrates),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                },
                                onValueChange = { newLogin -> carbohydratesState.value = newLogin },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                leadingIcon = {
                                    Image(
                                        painterResource(R.drawable.carbohydrates),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )
                                }
                            )
                        }
                    }
                    ButtonBlue(text = LocalContext.current.getString(R.string.add)) {
                        FoodActivity().addKPFC(
                            context,
                            kilocaloriesState.value,
                            proteinsState.value,
                            fatsState.value,
                            carbohydratesState.value,
                            DateToday().getToday()
                        )
                        openDialog = false
                    }
                }
            }
        }
    }
}