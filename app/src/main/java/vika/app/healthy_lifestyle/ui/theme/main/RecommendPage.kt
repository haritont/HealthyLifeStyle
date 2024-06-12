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
import vika.app.healthy_lifestyle.bean.main.Type
import vika.app.healthy_lifestyle.bean.sport.PhysicalExercise
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
    var recommendPhysicalExercise: PhysicalExercise? = null
    var recommendValue = 0.0
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
                if (recommend.getMarkTopKilo(meal) == 1.0){
                    val rec  = recommend.getPhysicalExercise(meal)
                    recommendPhysicalExercise = rec.first
                    recommendValue = rec.second
                }
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
                    color = if (markKilo == 0.5 || markKilo == -1.0) Orange else if (markKilo == 0.0) Red else Color.Gray
                )
                Text(
                    text = recommend.progressProtein,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (markProtein == 0.5|| markProtein == -1.0) Orange else if (markProtein == 0.0) Red else Color.Gray
                )
                Text(
                    text = recommend.progressFat,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (markFat == 0.5 || markFat == -1.0) Orange else if (markFat == 0.0) Red else Color.Gray
                )
                Text(
                    text = recommend.progressCarb,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (markCarb == 0.5 || markCarb == -1.0) Orange else if (markCarb == 0.0) Red else Color.Gray
                )
            }
        }
        val productsList = mutableListOf<ProductMark>()
        for (productMeal in mealList){
            val product = FoodActivity().getIngredient(context, productMeal.name)
            val mark = recommend.getMarkProduct(product, productMeal.value)
            productsList.add(
                ProductMark(
                    name = productMeal.name,
                    kilocalories = product.kilocalories,
                    proteins = product.proteins,
                    fats = product.fats,
                    carbohydrates = product.carbohydrates,
                    value = productMeal.value,
                    exception = product.exception,
                    favorite = product.favorite,
                    mark = mark
                )
            )
        }
        LazyColumn(
            modifier = Modifier
                .height(200.dp)
                .padding(8.dp)
        ) {
            items(productsList) { item ->
                key(item.name) {
                    RecommendItem(
                        title = item.name,
                        color =
                        if (item.favorite) {
                            Blue
                        } else if (item.exception) {
                            Red
                        } else if (item.mark < 0) {
                            Red
                        } else {
                            Green
                        },
                        kilocalories = item.kilocalories * item.value / 100,
                        proteins = item.proteins * item.value / 100,
                        fats = item.fats * item.value / 100,
                        carbohydrates = item.carbohydrates * item.value / 100,
                        value = item.value
                    )
                }
            }
        }
        var openDialog by remember { mutableStateOf(false) }

        if (recommendPhysicalExercise != null){
            Column {
                Text(text = "Рекомендованное упражнение")
                ItemListValue(
                    title = recommendPhysicalExercise!!.name,
                    value = recommendValue
                )
            }
        }
        Button(onClick = { openDialog = true }) {
            Text(text = "Рекомендации")
        }
        if (openDialog) {
            val newProductList = recommend.getReplaceProduct(productsList)
            val newMeal = Ingredient(
                name = "Рекомендуемый набор продуктов", kilocalories = 0.0, proteins = 0.0,
                fats = 0.0, carbohydrates = 0.0, type = Type()
            )
            for (product in newProductList){
                newMeal.kilocalories += product.value * product.kilocalories / 100
                newMeal.proteins += product.value * product.proteins / 100
                newMeal.fats += product.value * product.fats / 100
                newMeal.carbohydrates += product.value * product.carbohydrates / 100
            }
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
                            val markKilo = recommend.getMarkKilo(newMeal)
                            val markProtein = recommend.getMarkProtein(newMeal)
                            val markFat = recommend.getMarkFat(newMeal)
                            val markCarb = recommend.getMarkCarb(newMeal)
                            Text(
                                text = "Новое значение",
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
                                color = if (markKilo == 0.5 || markKilo == -1.0) Orange else if (markKilo == 0.0) Red else Color.Gray
                            )
                            Text(
                                text = recommend.progressProtein,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (markProtein == 0.5|| markProtein == -1.0) Orange else if (markProtein == 0.0) Red else Color.Gray
                            )
                            Text(
                                text = recommend.progressFat,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (markFat == 0.5 || markFat == -1.0) Orange else if (markFat == 0.0) Red else Color.Gray
                            )
                            Text(
                                text = recommend.progressCarb,
                                modifier = Modifier.padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (markCarb == 0.5 || markCarb == -1.0) Orange else if (markCarb == 0.0) Red else Color.Gray
                            )
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(8.dp)
                    ) {
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