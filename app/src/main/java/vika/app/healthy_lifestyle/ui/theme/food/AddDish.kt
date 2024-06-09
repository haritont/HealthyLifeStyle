package vika.app.healthy_lifestyle.ui.theme.food

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.RedLight
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue
import vika.app.healthy_lifestyle.ui.theme.general.defaultOptionProduct
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListText
import vika.app.healthy_lifestyle.ui.theme.general.list.Search

@Composable
fun AddDish(
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit,
    getAdd: (name:String, type:String) -> Unit
) {
    var openDialog by remember { mutableStateOf(isOpen) }

    val context = LocalContext.current

    val itemListIngredient = mutableListOf<Item>()
    val ingredients = FoodActivity().getAllProducts(context)
    if (ingredients != null) {
        for (ingredient in ingredients) {
            itemListIngredient.add(
                Item(
                    ingredient.name,
                    ingredient.type.type,
                    ingredient.favorite,
                    ingredient.exception,
                    if (ingredient.isDish) 1 else 0
                )
            )
        }
    }
    var filteredListIngredient by remember { mutableStateOf(itemListIngredient) }
    val selectListIngredient = remember { mutableStateListOf<ItemText>() }

    val nameState = remember { mutableStateOf("") }

    var options = TypeRepository(context).getAllByProduct()
    if (options!!.isEmpty()){
        options = defaultOptionProduct
    }

    val typeState = remember { mutableStateOf(options[0]) }

    if (isOpen != openDialog) {
        openDialog = isOpen
    }

    DisposableEffect(isOpen) {
        onOpenChange(openDialog)
        onDispose { }
    }

    var colorAdd by remember {
        mutableStateOf(Color.Transparent)
    }

    var colorName by remember {
        mutableStateOf(Color.Transparent)
    }

    if (isOpen) {
        Dialog(
            onDismissRequest = {
                openDialog = !openDialog
                onOpenChange(openDialog)
            }
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Dropdown(
                            options,
                            options[0]
                        ) { currentOption ->
                            typeState.value = currentOption
                        }

                        Text(
                            text = "Добавить блюдо",
                            modifier = Modifier.padding(8.dp),
                            fontWeight = FontWeight.Bold,
                            color = Black
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .border(3.dp, colorAdd, RoundedCornerShape(10.dp))
                        )
                        {
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
                                        painterResource(R.drawable.dish),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(25.dp)
                                    )
                                }
                            )
                        }

                        Search(
                            itemList = itemListIngredient,
                            onSearchResults = {
                                filteredListIngredient = it.toMutableList()
                            }
                        )

                        LazyColumn(
                            modifier = Modifier
                                .width(300.dp)
                                .height(200.dp)
                        ) {
                            items(filteredListIngredient) { item ->
                                key(item.title) {
                                    ItemListText(
                                        title = item.title,
                                        textInDialog = "Введите вес в гр.",
                                        add = { title, value ->
                                            selectListIngredient.add(ItemText(title, value))
                                        }
                                    )
                                }
                            }
                        }

                        Text(
                            text = "Добавленные ингредиенты",
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
                                items(selectListIngredient) { item ->
                                    key(item) {
                                        ItemListDelete(
                                            title = item.title,
                                            value = item.value,
                                            delete = { title ->
                                                selectListIngredient.remove(
                                                    selectListIngredient.find { it.title == title }
                                                )
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                onClick = {
                                    var check = true
                                    if (nameState.value == ""){
                                        check = false
                                        colorName = RedLight
                                    }
                                    if (selectListIngredient.size == 0){
                                        check = false
                                        colorAdd = RedLight
                                    }
                                    if (check) {
                                        FoodActivity().addNewDish(
                                            context,
                                            nameState.value,
                                            typeState.value,
                                            selectListIngredient
                                        )
                                        openDialog = !openDialog
                                        onOpenChange(openDialog)
                                        Toast.makeText(context, "Создано: ".plus(nameState.value), Toast.LENGTH_SHORT).show()
                                        getAdd(nameState.value, typeState.value)
                                    }
                                },
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Text("Добавить")
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