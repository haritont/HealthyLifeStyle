package vika.app.healthy_lifestyle.ui.theme.general.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.bean.Item

@Composable
fun SearchList (
    itemList: List<Item>,
    onSearchResults: (List<Item>) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    TextField(
        value = searchQuery,
        label = {
            Text(
                text = "Поиск"
            )
        },
        onValueChange = {
            searchQuery = it
            val filteredList = itemList.filter { item ->
                item.title.contains(searchQuery, ignoreCase = true)
            }
            onSearchResults(filteredList)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        leadingIcon = {
            Image(
                painterResource(R.drawable.search),
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
            )
        }
    )
}