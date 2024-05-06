package vika.app.healthy_lifestyle.base.data.mapper.food

import vika.app.healthy_lifestyle.base.data.entity.food.DishEntity
import vika.app.healthy_lifestyle.bean.food.Dish


interface DishMapper {
    fun toDishEntity(dish: Dish): DishEntity
    fun toDish(dishEntity: DishEntity): Dish
    fun toDishList(dishEntities: List<DishEntity>): List<Dish>
}

class DefaultDishMapper : DishMapper {
    override fun toDishEntity(dish: Dish): DishEntity {
        return DishEntity(
            id = dish.id,
            name = dish.name,
            kilocalories = dish.kilocalories,
            proteins = dish.proteins,
            fats = dish.fats,
            carbohydrates = dish.carbohydrates,
            favorite = dish.favorite,
            exception = dish.exception,
            type = dish.type
        )
    }

    override fun toDish(dishEntity: DishEntity): Dish {
        return Dish(
            id = dishEntity.id,
            name = dishEntity.name,
            kilocalories = dishEntity.kilocalories,
            proteins = dishEntity.proteins,
            fats = dishEntity.fats,
            carbohydrates = dishEntity.carbohydrates,
            favorite = dishEntity.favorite,
            exception = dishEntity.exception,
            type = dishEntity.type
        )
    }

    override fun toDishList(dishEntities: List<DishEntity>): List<Dish> {
        val dishes = mutableListOf<Dish>()
        for (dishEntity in dishEntities){
            dishes.add(toDish(dishEntity))
        }
        return dishes
    }
}