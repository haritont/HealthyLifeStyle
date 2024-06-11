package vika.app.healthy_lifestyle.ui.theme.navigation.screens.main

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import vika.app.healthy_lifestyle.R
import vika.app.healthy_lifestyle.activity.food.FoodActivity
import vika.app.healthy_lifestyle.base.data.repository.food.IngredientRepository
import vika.app.healthy_lifestyle.base.data.repository.food.NutritionRepository
import vika.app.healthy_lifestyle.base.data.repository.main.BarcodeRepository
import vika.app.healthy_lifestyle.bean.Item
import vika.app.healthy_lifestyle.bean.food.Nutrition
import vika.app.healthy_lifestyle.bean.main.Barcode
import vika.app.healthy_lifestyle.calculation.DateToday
import vika.app.healthy_lifestyle.calculation.MealCalc
import vika.app.healthy_lifestyle.ui.theme.food.AddIngredient
import vika.app.healthy_lifestyle.ui.theme.general.ImageButton
import vika.app.healthy_lifestyle.ui.theme.general.list.ItemListText
import vika.app.healthy_lifestyle.ui.theme.general.list.Search

@Composable
fun RequestCameraPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
}

@Composable
fun BarcodeScannerScreen() {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var checkBarcode by remember { mutableStateOf(false) }

    if (checkBarcode) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ItemListText(
                title = name,
                textInDialog = "Введите вес в гр/мл.",
                add = { title, value ->
                    NutritionRepository(context).insertNutrition(
                        Nutrition(
                            name = title,
                            value = value,
                            date = DateToday().getToday(),
                            meal = MealCalc().getCurrentMeal()
                        )
                    )
                }
            )
        }
    }

    var checkBarcodeNull by remember { mutableStateOf(false) }
    var code by remember { mutableStateOf("") }

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
    var openDialogAddIngredient by remember { mutableStateOf(false) }
    if (checkBarcodeNull) {
        Column (
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AddIngredient(
                openDialogAddIngredient,
                onOpenChange = { openDialogAddIngredient = it },
                getAdd = {name, type ->
                    filteredListIngredient.add(
                        Item(
                            name,
                            type,
                            favorite = false,
                            exception = false,
                            typeIs = 0
                        )
                    )
                }
            )
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Search(
                    itemList = itemListIngredient,
                    onSearchResults = {
                        filteredListIngredient = it.toMutableList()
                    }
                )
                ImageButton(
                    icon = R.drawable.add
                ) {
                    openDialogAddIngredient = true
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredListIngredient) { item ->
                    key(item.title) {
                        ItemListText(
                            title = item.title,
                            textInDialog = "Введите вес в гр/мл.",
                            add = { title, value ->
                                NutritionRepository(context).insertNutrition(
                                    Nutrition(
                                        name = title,
                                        value = value,
                                        date = DateToday().getToday(),
                                        meal = MealCalc().getCurrentMeal()
                                    )
                                )
                                val ingredient =
                                    IngredientRepository(context).getIngredientByName(title)
                                BarcodeRepository(context).insertBarcode(
                                    Barcode(
                                        code = code,
                                        idIngredient = ingredient.id
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    var hasCameraPermission by remember { mutableStateOf(false) }

    RequestCameraPermission {
        hasCameraPermission = true
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    val barcodeLauncher = rememberLauncherForActivityResult(
        contract = ScanContract()
    ) { result ->
        if (result.contents != null) {
            val scannedContent = result.contents
            val barcode = BarcodeRepository(context).getByCode(scannedContent)

            if (barcode != null){
                val ingredient = IngredientRepository(context).getIngredientById(barcode.idIngredient)
                name = ingredient.name
                checkBarcode = true
            }
            else{
                checkBarcodeNull = true
                code = scannedContent
            }
        }
    }

    if (hasCameraPermission) {
        LaunchedEffect(Unit) {
            barcodeLauncher.launch(ScanOptions())
        }
    }
}
