package vika.app.healthy_lifestyle.ui.theme.sport

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.sport.SportActivity
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.RedLight
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListText
import vika.app.healthy_lifestyle.ui.theme.general.list.Search

@Composable
fun MoreTraining(
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit,
    title: String
) {
    var openDialog by remember { mutableStateOf(isOpen) }

    val context = LocalContext.current

    if (isOpen != openDialog) {
        openDialog = isOpen
    }

    DisposableEffect(isOpen) {
        openDialog = isOpen
        onDispose { }
    }

    if (openDialog) {
        val training = SportActivity().getPhysicalExerciseByName(context, title)

        val itemListPhysicalExercise = mutableListOf<Item>()
        val physicalExercises = SportActivity().getAllPhysicalExercises(context)
        for (physicalExercise in physicalExercises) {
            itemListPhysicalExercise.add(
                Item(
                    physicalExercise.name,
                    physicalExercise.type.type,
                    physicalExercise.favorite,
                    physicalExercise.exception,
                    if (physicalExercise.training) 3 else 2
                )
            )
        }
        var filteredListPhysicalExercise by remember { mutableStateOf(itemListPhysicalExercise) }

        val trainingList = SportActivity().getTraining(context, training.id)
        val selectListPhysicalExercise = remember { mutableStateListOf<ItemText>() }
        remember {
            trainingList.forEach { train ->
                val exerciseName =
                    SportActivity().getPhysicalExerciseById(context, train.idPhysicalExercise).name
                if (selectListPhysicalExercise.none { it.title == exerciseName }) {
                    selectListPhysicalExercise.add(
                        ItemText(
                            exerciseName,
                            train.valuePhysicalExercise
                        )
                    )
                }
            }
        }

        val nameState = remember { mutableStateOf(title) }
        val metState = remember { mutableStateOf(training.met.toString()) }

        val options = TypeRepository(context).getAllByPhys()!!
        val typeState = remember { mutableStateOf(training.type.type) }

        var favoriteState by remember { mutableStateOf(training.favorite) }
        var exceptionState by remember { mutableStateOf(training.exception) }

        var colorAdd by remember {
            mutableStateOf(Color.Transparent)
        }

        var colorName by remember {
            mutableStateOf(Color.Transparent)
        }

        Dialog(
            onDismissRequest = {
                openDialog = false
                onOpenChange(false)
            }
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                )
                {
                    Text(
                        text = title,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        color = Black
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Dropdown(
                            options,
                            training.type.type
                        ) { currentOption ->
                            typeState.value = currentOption
                        }

                        Image(
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        exceptionState = !exceptionState
                                    })
                                .size(35.dp)
                                .padding(8.dp),
                            painter = painterResource(if (!exceptionState) R.drawable.exception_false else R.drawable.exception_true),
                            contentDescription = null
                        )

                        Image(
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        favoriteState = !favoriteState
                                    })
                                .size(35.dp)
                                .padding(8.dp),
                            painter = painterResource(if (!favoriteState) R.drawable.like_false else R.drawable.like_true),
                            contentDescription = null
                        )
                    }

                    LazyColumn {
                        item {
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier
                                    .border(3.dp, colorName, RoundedCornerShape(10.dp))
                            ) {
                                TextFieldBlue(
                                    value = nameState.value,
                                    label = {
                                        Text(
                                            LocalContext.current.getString(R.string.input_name),
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                    },
                                    onValueChange = { newLogin -> nameState.value = newLogin },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                                    leadingIcon = {
                                        Image(
                                            painterResource(R.drawable.sport),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(25.dp)
                                        )
                                    }
                                )
                            }

                            LazyColumn(
                                modifier = Modifier.height(500.dp)
                            ) {
                                item {
                                    Text(
                                        text = "Добавленные упражнения",
                                        modifier = Modifier.padding(8.dp),
                                        fontWeight = FontWeight.Bold,
                                        color = Black
                                    )

                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier
                                            .border(3.dp, colorAdd, RoundedCornerShape(10.dp))
                                    ) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .width(300.dp)
                                                .height(200.dp)
                                        ) {
                                            items(selectListPhysicalExercise) { item ->
                                                key(item) {
                                                    ItemListDelete(
                                                        title = item.title,
                                                        value = item.value,
                                                        delete = { title ->
                                                            selectListPhysicalExercise.remove(
                                                                selectListPhysicalExercise.find { it.title == title }
                                                            )
                                                            val physicalExercise =
                                                                SportActivity().getPhysicalExerciseByName(
                                                                    context,
                                                                    title
                                                                )
                                                            metState.value =
                                                                (metState.value.toDouble() - (item.value / 60.0) * physicalExercise.met).toString()
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    Search(
                                        itemList = itemListPhysicalExercise,
                                        onSearchResults = {
                                            filteredListPhysicalExercise = it.toMutableList()
                                        }
                                    )

                                    LazyColumn(
                                        modifier = Modifier
                                            .width(300.dp)
                                            .height(200.dp)
                                    ) {
                                        items(filteredListPhysicalExercise) { item ->
                                            key(item.title) {
                                                ItemListText(
                                                    title = item.title,
                                                    textInDialog = "Введите время выполнения в мин",
                                                    add = { title, value ->
                                                        if (selectListPhysicalExercise.none { it.title == title }) {
                                                            selectListPhysicalExercise.add(
                                                                ItemText(
                                                                    title,
                                                                    value
                                                                )
                                                            )
                                                        }
                                                        val physicalExercise =
                                                            SportActivity().getPhysicalExerciseByName(
                                                                context,
                                                                title
                                                            )
                                                        metState.value =
                                                            (metState.value.toDouble() + (value / 60.0) * physicalExercise.met).toString()
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextButton(
                                    onClick = {
                                        var check = true
                                        if (nameState.value == "") {
                                            check = false
                                            colorName = RedLight
                                        }
                                        if (selectListPhysicalExercise.size == 0) {
                                            check = false
                                            colorAdd = RedLight
                                        }
                                        if (check) {
                                            SportActivity().updateTraining(
                                                context,
                                                training.id,
                                                nameState.value,
                                                metState.value.replace(",", ".").toDouble(),
                                                typeState.value,
                                                favoriteState,
                                                exceptionState,
                                                selectListPhysicalExercise
                                            )
                                            openDialog = false
                                            onOpenChange(false)
                                            Toast.makeText(
                                                context,
                                                "Изменено: ".plus(nameState.value),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    modifier = Modifier.padding(8.dp),
                                ) {
                                    Text("Ок")
                                }
                                TextButton(
                                    onClick = {
                                        openDialog = false
                                        onOpenChange(false)
                                    },
                                    modifier = Modifier.padding(8.dp),
                                ) {
                                    Text("Отмена")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}