package vika.app.healthy_lifestyle.base.data.mapper.food

import vika.app.healthy_lifestyle.base.data.entity.food.RecipeEntity
import vika.app.healthy_lifestyle.bean.food.Recipe

interface RecipeMapper {
    fun toRecipeEntity(recipe: Recipe): RecipeEntity
    fun toRecipe(recipeEntity: RecipeEntity): Recipe
    fun toRecipeList(recipeEntities: List<RecipeEntity>): List<Recipe>
}

class DefaultRecipeMapper: RecipeMapper {
    override fun toRecipeEntity(recipe: Recipe): RecipeEntity {
        return RecipeEntity(
            recipe.id,
            recipe.idDish,
            recipe.idIngredient,
            recipe.valueIngredient
        )
    }

    override fun toRecipe(recipeEntity: RecipeEntity): Recipe {
        return Recipe(
            recipeEntity.id,
            recipeEntity.idDish,
            recipeEntity.idIngredient,
            recipeEntity.valueIngredient
        )
    }

    override fun toRecipeList(recipeEntities: List<RecipeEntity>): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        for (recipeEntity in recipeEntities){
            recipes.add(toRecipe(recipeEntity))
        }
        return recipes
    }
}