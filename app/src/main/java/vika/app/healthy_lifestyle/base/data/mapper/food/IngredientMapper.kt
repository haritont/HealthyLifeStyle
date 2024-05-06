package vika.app.healthy_lifestyle.base.data.mapper.food

import vika.app.healthy_lifestyle.base.data.entity.food.IngredientEntity
import vika.app.healthy_lifestyle.bean.food.Ingredient

interface IngredientMapper {
    fun toIngredientEntity(ingredient: Ingredient): IngredientEntity
    fun toIngredient(ingredientEntity: IngredientEntity): Ingredient
    fun toIngredientList(ingredientEntities: List<IngredientEntity>): List<Ingredient>
}

class DefaultIngredientMapper : IngredientMapper {
    override fun toIngredientEntity(ingredient: Ingredient): IngredientEntity {
        return IngredientEntity(
            id = ingredient.id,
            name = ingredient.name,
            kilocalories = ingredient.kilocalories,
            proteins = ingredient.proteins,
            fats = ingredient.fats,
            carbohydrates = ingredient.carbohydrates,
            favorite = ingredient.favorite,
            exception = ingredient.exception,
            ingredient.type
        )
    }

    override fun toIngredient(ingredientEntity: IngredientEntity): Ingredient {
        return Ingredient(
            id = ingredientEntity.id,
            name = ingredientEntity.name,
            kilocalories = ingredientEntity.kilocalories,
            proteins = ingredientEntity.proteins,
            fats = ingredientEntity.fats,
            carbohydrates = ingredientEntity.carbohydrates,
            favorite = ingredientEntity.favorite,
            exception = ingredientEntity.exception,
            ingredientEntity.type
        )
    }

    override fun toIngredientList(ingredientEntities: List<IngredientEntity>): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        for (dishEntity in ingredientEntities){
            ingredients.add(toIngredient(dishEntity))
        }
        return ingredients
    }
}