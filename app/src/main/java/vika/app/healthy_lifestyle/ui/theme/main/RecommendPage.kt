package vika.app.healthy_lifestyle.ui.theme.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.food.Nutrition
import vika.app.healthy_lifestyle.recommend.RecommendSystem
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Blue
import vika.app.healthy_lifestyle.ui.theme.app.Green
import vika.app.healthy_lifestyle.ui.theme.app.Orange
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListValue
import vika.app.healthy_lifestyle.ui.theme.general.list.RecommendItem

@Composable
fun RecommendPage(
    text: String,
    recommend: RecommendSystem,
    meal: Ingredient,
    mealList: List<Nutrition>
) {
    val context = LocalContext.current
    Text(
        text = text,
        modifier = Modifier.padding(8.dp),
        fontWeight = FontWeight.Bold,
        color = Black
    )
    if (mealList.isNotEmpty()) {
        Row {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                recommend.getTarget()
                Text(
                    text = "Цель",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Text(
                    text = recommend.targetKilo,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Text(
                    text = recommend.targetProtein,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Text(
                    text = recommend.targetFat,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Text(
                    text = recommend.targetCarb,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val markKilo = recommend.getMarkKilo(meal)
                val markProtein = recommend.getMarkProtein(meal)
                val markFat = recommend.getMarkFat(meal)
                val markCarb = recommend.getMarkCarb(meal)
                Text(
                    text = "Ваше значение",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
                Text(
                    text = recommend.progressKilo,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (markKilo == 0.5) Orange else if (markKilo == 0.0) Red else Color.Gray
                )
                Text(
                    text = recommend.progressProtein,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (markProtein == 0.5) Orange else if (markProtein == 0.0) Red else Color.Gray
                )
                Text(
                    text = recommend.progressFat,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (markFat == 0.5) Orange else if (markFat == 0.0) Red else Color.Gray
                )
                Text(
                    text = recommend.progressCarb,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (markCarb == 0.5) Orange else if (markCarb == 0.0) Red else Color.Gray
                )
            }
        }
        val productsList = mutableListOf<ProductMark>()
        LazyColumn(
            modifier = Modifier
                .height(200.dp)
                .padding(8.dp)
        ) {
            items(mealList) { item ->
                key(item.name) {
                    val product = FoodActivity().getIngredient(context, item.name)
                    val mark = recommend.getMarkProduct(product, item.value)
                    productsList.add(
                        ProductMark(
                            name = item.name,
                            kilocalories = product.kilocalories,
                            proteins = product.proteins,
                            fats = product.fats,
                            carbohydrates = product.carbohydrates,
                            value = item.value,
                            exception = product.exception,
                            favorite = product.favorite,
                            mark = mark
                        )
                    )
                    RecommendItem(
                        title = item.name,
                        color =
                        if (product.favorite) {
                            Blue
                        } else if (product.exception) {
                            Red
                        } else if (mark < 0) {
                            Red
                        } else {
                            Green
                        },
                        kilocalories = product.kilocalories * item.value / 100,
                        proteins = product.proteins * item.value / 100,
                        fats = product.fats * item.value / 100,
                        carbohydrates = product.carbohydrates * item.value / 100
                    )
                }
            }
        }
        var openDialog by remember { mutableStateOf(false) }
        Button(onClick = { openDialog = true }) {
            Text(text = "Рекомендации")
        }
        if (openDialog) {
            Dialog(
                onDismissRequest = {
                    openDialog = !openDialog
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
                        val newProductList = recommend.getReplaceProduct(productsList)
                        LazyColumn(
                            modifier = Modifier
                                .height(300.dp)
                                .padding(8.dp)
                        ) {
                            items(newProductList) { item ->
                                key(item.name) {
                                    ItemListValue(
                                        title = if (item.replacement == "") item.name else item.replacement,
                                        value = item.value
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class ProductMark(
    val name: String,
    val kilocalories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val value: Double,
    val mark: Int,
    val exception: Boolean,
    val favorite: Boolean,
    var replacement: String = ""
)