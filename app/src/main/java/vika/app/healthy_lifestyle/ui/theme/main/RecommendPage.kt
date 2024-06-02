package vika.app.healthy_lifestyle.ui.theme.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.bean.food.Ingredient
import vika.app.healthy_lifestyle.bean.food.Nutrition
import vika.app.healthy_lifestyle.recommend.RecommendSystem
import vika.app.healthy_lifestyle.recommend.database.RecommendProductRepository
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.app.Green
import vika.app.healthy_lifestyle.ui.theme.app.Orange
import vika.app.healthy_lifestyle.ui.theme.app.Red
import vika.app.healthy_lifestyle.ui.theme.app.Yellow
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
        LazyColumn(
            modifier = Modifier
                .height(300.dp)
                .padding(8.dp)
        ) {
            items(mealList) { item ->
                key(item.name) {
                    val product = FoodActivity().getIngredient(context, item.name)
                    val mark =
                        RecommendProductRepository(context).getRecommendProduct(item.name).mark
                    RecommendItem(
                        title = item.name,
                        color =
                        if (mark >= 3) {
                            Green
                        } else if (mark >= 2) {
                            Yellow
                        } else if (mark >= 1) {
                            Orange
                        } else {
                            Red
                        },
                        kilocalories = product.kilocalories * item.value / 100,
                        proteins = product.proteins * item.value / 100,
                        fats = product.fats * item.value / 100,
                        carbohydrates = product.carbohydrates * item.value / 100
                    )
                }
            }
        }
    }
}