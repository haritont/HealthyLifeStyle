package vika.app.healthy_lifestyle.base.data.mapper.food

import android.content.Context
import vika.app.healthy_lifestyle.base.data.entity.food.IngredientEntity
import vika.app.healthy_lifestyle.base.data.repository.main.TypeRepository
import vika.app.healthy_lifestyle.bean.food.Ingredient

interface IngredientMapper {
    fun toIngredientEntity(ingredient: Ingredient): IngredientEntity
    fun toIngredient(ingredientEntity: IngredientEntity, context: Context): Ingredient
    fun toIngredientList(ingredientEntities: List<IngredientEntity>, context: Context): List<Ingredient>
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
            type = ingredient.type.type,
            isDish = ingredient.isDish
        )
    }

    override fun toIngredient(ingredientEntity: IngredientEntity, context: Context): Ingredient {
        return Ingredient(
            id = ingredientEntity.id,
            name = ingredientEntity.name,
            kilocalories = ingredientEntity.kilocalories,
            proteins = ingredientEntity.proteins,
            fats = ingredientEntity.fats,
            carbohydrates = ingredientEntity.carbohydrates,
            favorite = ingredientEntity.favorite,
            exception = ingredientEntity.exception,
            type = TypeRepository(context).getByName(ingredientEntity.type),
            isDish = ingredientEntity.isDish
        )
    }

    override fun toIngredientList(ingredientEntities: List<IngredientEntity>, context: Context): List<Ingredient> {
        val ingredients = mutableListOf<Ingredient>()
        for (dishEntity in ingredientEntities){
            ingredients.add(toIngredient(dishEntity, context))
        }
        return ingredients
    }
}