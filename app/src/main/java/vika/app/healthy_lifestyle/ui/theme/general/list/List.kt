package vika.app.healthy_lifestyle.ui.theme.general.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.ui.theme.food.AddDish
import vika.app.healthy_lifestyle.ui.theme.food.AddIngredient
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton


@Composable
fun List(
    itemList: List<Item>,
    add: (name: String, value: Double, date: String, option: String) -> Unit,
    typeToMore: Int,
    updateException: (name: String, exception: Boolean) -> Unit,
    updateFavorite: (name: String, favorite: Boolean) -> Unit,
    textInDialog: String,
    options: List<String>,
    firstOption: String,
    typeAdd: Int, //0-ingred, 1-dish, 2-physEx, 3-training
) {
    var filteredList by remember { mutableStateOf(itemList) }

    var searchKey by remember { mutableStateOf(0) }

    var openDialogAddIngredient by remember { mutableStateOf(false) }
    var openDialogAddDish by remember { mutableStateOf(false) }

    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Search(
                itemList = itemList
            ) {
                filteredList = it
                searchKey = 1
            }
            AddIngredient(
                openDialogAddIngredient,
                onOpenChange = { openDialogAddIngredient = it }
            )
            AddDish(
                openDialogAddDish,
                onOpenChange = { openDialogAddDish = it }
            )
            ImageButton(
                icon = R.drawable.add
            ) {
                when (typeAdd) {
                    0 -> {
                        openDialogAddIngredient = !openDialogAddIngredient
                    }

                    1 -> {
                        openDialogAddDish = !openDialogAddDish
                    }

                    2 -> {

                    }

                    3 -> {

                    }
                }
            }
        }

        val listState = rememberLazyListState()

        if (searchKey == 1) {
            LaunchedEffect(listState) {
                listState.scrollToItem(index = 0, 0)
            }
        }

        LazyColumn (
            state = listState
        ){
            items(filteredList) { item ->
                key(item.title) {
                    ItemList(
                        title = item.title,
                        emoji = item.emoji,
                        favorite = item.favorite,
                        exception = item.exception,
                        add = { name, value, date, option -> add(name, value, date, option) },
                        typeToMore = typeToMore,
                        updateException = { name, exception -> updateException(name, exception) },
                        updateFavorite = { name, favorite -> updateFavorite(name, favorite) },
                        textInDialog = textInDialog,
                        options = options,
                        firstOption = firstOption
                    )
                }
            }
        }
    }
}
