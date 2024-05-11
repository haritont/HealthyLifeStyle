package vika.app.healthy_lifestyle.ui.theme.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.ItemText
import vika.app.healthy_lifestyle.ui.theme.app.Black
import vika.app.healthy_lifestyle.ui.theme.general.Dropdown
import vika.app.healthy_lifestyle.ui.theme.general.TextFieldBlue
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListDelete
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListText
import vika.app.healthy_lifestyle.ui.theme.general.list.Search

@Composable
fun MoreDish(
    isOpen: Boolean,
    onOpenChange: (Boolean) -> Unit,
    title: String
){
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
         val dish = FoodActivity().getDish(context, title)

         val itemListIngredient = mutableListOf<Item>()
         val ingredients = FoodActivity().getAllIngredients(context)
         for (ingredient in ingredients) {
             itemListIngredient.add(
                 Item(
                     ingredient.name,
                     ingredient.type,
                     ingredient.favorite,
                     ingredient.exception
                 )
             )
         }
         var filteredListIngredient by remember { mutableStateOf(itemListIngredient) }

         val recipeList =  FoodActivity().getRecipe(context, dish.id)
         val selectListIngredient = remember { mutableStateListOf<ItemText>() }
         for (recipe in recipeList){
             selectListIngredient.add(
                 ItemText(
                     title = FoodActivity().getIngredient(context, recipe.idIngredient).name,
                     value = recipe.valueIngredient
                 )
             )
         }

         val nameState = remember { mutableStateOf(title) }
         val kilocaloriesState = remember { mutableStateOf(dish.kilocalories) }
         val proteinsState = remember { mutableStateOf(dish.proteins) }
         val fatsState = remember { mutableStateOf(dish.fats) }
         val carbohydratesState = remember { mutableStateOf(dish.carbohydrates) }

         val options = listOf(
             "Без типа",
             "Напиток",
             "Фрукт",
             "Сладкое",
             "Приправа",
             "Алкоголь",
             "Дичь",
             "Рыба",
             "Орех",
             "Ягода",
             "Вода",
             "Овощ",
             "Мучное",
             "Зелень",
             "Соус",
             "Уксус",
             "Мясо",
             "Субпродукт",
             "Сыр",
             "Боб",
             "Крупа",
             "Гриб",
             "Молочное",
             "Масло",
             "Яйцо"
         )
         val typeState = remember { mutableStateOf(dish.type) }

         var favoriteState by remember { mutableStateOf(dish.favorite) }
         var exceptionState by remember { mutableStateOf(dish.exception) }

         Dialog(
             onDismissRequest = {
                 openDialog = false
                 onOpenChange(openDialog)
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
                     Row(
                         horizontalArrangement = Arrangement.SpaceAround,
                         verticalAlignment = Alignment.CenterVertically
                     ) {
                         Dropdown(
                             options,
                             dish.type
                         ) {
                                 currentOption ->
                             typeState.value = currentOption
                         }

                         Text(
                             text = title,
                             modifier = Modifier.padding(8.dp),
                             fontWeight = FontWeight.Bold,
                             color = Black
                         )

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

                     Spacer(modifier = Modifier.height(10.dp))
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

                     LazyColumn (
                         modifier = Modifier.height(500.dp)
                     ){
                         item {
                             Text(
                                 text = "Добавленные ингредиенты",
                                 modifier = Modifier.padding(8.dp),
                                 fontWeight = FontWeight.Bold,
                                 color = Black
                             )

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
                                                 val ingredient =
                                                     FoodActivity().getIngredient(context, title)
                                                 kilocaloriesState.value -= ingredient.kilocalories / 100 * item.value
                                                 proteinsState.value -= ingredient.proteins / 100 * item.value
                                                 fatsState.value -= ingredient.fats / 100 * item.value
                                                 carbohydratesState.value -= ingredient.carbohydrates / 100 * item.value
                                             }
                                         )
                                     }
                                 }
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
                                                 val ingredient =
                                                     FoodActivity().getIngredient(context, title)
                                                 kilocaloriesState.value += ingredient.kilocalories / 100 * value
                                                 proteinsState.value += ingredient.proteins / 100 * value
                                                 fatsState.value += ingredient.fats / 100 * value
                                                 carbohydratesState.value += ingredient.carbohydrates / 100 * value
                                             }
                                         )
                                     }
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
                                         value = "%.1f".format(kilocaloriesState.value),
                                         label = {
                                             Text(
                                                 LocalContext.current.getString(R.string.kilocalories),
                                                 style = MaterialTheme.typography.bodySmall
                                             )
                                         },
                                         onValueChange = { newLogin ->
                                             kilocaloriesState.value = newLogin.toDouble()
                                         },
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
                                         value = "%.1f".format(proteinsState.value),
                                         label = {
                                             Text(
                                                 LocalContext.current.getString(R.string.proteins),
                                                 style = MaterialTheme.typography.bodySmall
                                             )
                                         },
                                         onValueChange = { newLogin ->
                                             proteinsState.value = newLogin.toDouble()
                                         },
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
                                         value = "%.1f".format(fatsState.value),
                                         label = {
                                             Text(
                                                 LocalContext.current.getString(R.string.fats),
                                                 style = MaterialTheme.typography.bodySmall
                                             )
                                         },
                                         onValueChange = { newLogin ->
                                             fatsState.value = newLogin.toDouble()
                                         },
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
                                         value = "%.1f".format(carbohydratesState.value),
                                         label = {
                                             Text(
                                                 LocalContext.current.getString(R.string.carbohydrates),
                                                 style = MaterialTheme.typography.bodySmall
                                             )
                                         },
                                         onValueChange = { newLogin ->
                                             carbohydratesState.value = newLogin.toDouble()
                                         },
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
                         }
                     }

                     Row(
                         horizontalArrangement = Arrangement.Center
                     ) {
                         TextButton(
                             onClick = {
                                 FoodActivity().updateDish(
                                     context,
                                     dish.id,
                                     nameState.value,
                                     kilocaloriesState.value.toString().replace(",", ".").toDouble(),
                                     proteinsState.value.toString().replace(",", ".").toDouble(),
                                     fatsState.value.toString().replace(",", ".").toDouble(),
                                     carbohydratesState.value.toString().replace(",", ".").toDouble(),
                                     typeState.value,
                                     favoriteState,
                                     exceptionState,
                                     selectListIngredient
                                 )
                                 openDialog = false
                                 onOpenChange(openDialog)
                             },
                             modifier = Modifier.padding(8.dp),
                         ) {
                             Text("Ок")
                         }
                         TextButton(
                             onClick = {
                                 openDialog = false
                                 onOpenChange(openDialog)
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