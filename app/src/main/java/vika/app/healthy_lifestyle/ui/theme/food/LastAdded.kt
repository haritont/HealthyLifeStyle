package vika.app.healthy_lifestyle.ui.theme.food

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete

@Composable
fun LastAdded(selectListProduct: MutableList<ItemText>, onDelete: (ItemText, String) -> Unit) {
    Text(
        text = LocalContext.current.getString(R.string.last_add),
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Bold,
        color = Black
    )
    LazyColumn(
        modifier = Modifier
            .height(150.dp)
            .padding(8.dp)
    ) {
        items(selectListProduct) { item ->
            key(item) {
                ItemListDelete(
                    title = item.title,
                    value = item.value,
                    delete = { title -> onDelete(item, title) }
                )
            }
        }
    }
}