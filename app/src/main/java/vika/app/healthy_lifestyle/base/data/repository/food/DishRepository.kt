package vika.app.healthy_lifestyle.base.data.repository.food

import android.content.Context
import kotlinx.coroutines.runBlocking
import vika.app.healthy_lifestyle.base.data.dao.food.DishDao
import vika.app.healthy_lifestyle.base.data.database.food.DishDatabase
import vika.app.healthy_lifestyle.base.data.mapper.food.DefaultDishMapper
import vika.app.healthy_lifestyle.base.data.mapper.food.DishMapper
import vika.app.healthy_lifestyle.bean.food.Dish

class DishRepository (context: Context){
    private val dishDao: DishDao
    private val dishDatabase: DishDatabase = DishDatabase.getInstance(context)
    private val dishMapper: DishMapper

    init {
        dishDao = dishDatabase.dishDao()
        dishMapper = DefaultDishMapper()
    }

    fun insertDish(dish: Dish) = runBlocking{
        dishDao.insert(dishMapper.toDishEntity(dish))
    }

    fun getAllDishes(): List<Dish> = runBlocking{
        dishMapper.toDishList(dishDao.getAll())
    }

    fun getDishByName(name: String): Dish = runBlocking{
        dishMapper.toDish(dishDao.getByName(name))
    }

    fun isDishExists(nameDish: String): Boolean  = runBlocking{
        dishDao.isDishExist(nameDish) != 0
    }

    fun getDishRowCount(): Int = runBlocking{
        dishDao.getRowCount()
    }

    fun updateDishFavorite(name: String, favorite: Boolean) = runBlocking{
        dishDao.updateFavorite(name, favorite)
    }

    fun updateDishException(name: String, exception: Boolean) = runBlocking{
        dishDao.updateException(name, exception)
    }
}