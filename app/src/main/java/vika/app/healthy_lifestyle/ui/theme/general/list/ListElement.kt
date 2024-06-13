package vika.app.healthy_lifestyle.ui.theme.general.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.ui.theme.food.AddIngredient
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.sport.AddPhysicalExercise


@Composable
fun ListElement(
    itemList: List<Item>,
    add: (name: String, value: Double, date: String, option: String) -> Unit,
    updateException: (name: String, exception: Boolean) -> Unit,
    updateFavorite: (name: String, favorite: Boolean) -> Unit,
    textInDialog: String,
    options: List<String>,
    firstOption: String,
    typeAdd: Int, //0-ingred, 1-physEx
    clickSearch:() -> Unit,
    getAdd: (name:String, type:String) -> Unit
) {
    var filteredList by remember { mutableStateOf(itemList) }

    var openDialogAddIngredient by remember { mutableStateOf(false) }
    var openDialogAddPhysicalExercise by remember { mutableStateOf(false) }

    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Search(
                itemList = itemList,
                onSearchResults = {
                    filteredList = it
                    clickSearch()
                }
            )

            AddIngredient(
                openDialogAddIngredient,
                onOpenChange = { openDialogAddIngredient = it },
                getAdd
            )

            AddPhysicalExercise(
                openDialogAddPhysicalExercise,
                onOpenChange = { openDialogAddPhysicalExercise = it },
                getAdd
            )

            ImageButton(
                icon = R.drawable.add
            ) {
                when (typeAdd) {
                    0 -> {
                        openDialogAddIngredient = true
                    }

                    1 -> {
                        openDialogAddPhysicalExercise = true
                    }
                }
            }
        }

        if (filteredList.isNotEmpty()) {
            LazyColumn {
                items(filteredList) { item ->
                    key(item.title) {
                        ItemList(
                            title = item.title,
                            emoji = item.emoji,
                            favorite = item.favorite,
                            exception = item.exception,
                            add = { name, value, date, option -> add(name, value, date, option) },
                            typeToMore = item.typeIs,
                            updateException = { name, exception ->
                                updateException(
                                    name,
                                    exception
                                )
                            },
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
}
