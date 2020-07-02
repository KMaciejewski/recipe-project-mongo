package com.km.repository;

import com.km.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    Set<Ingredient> findAllByRecipeId(String recipeId);

    Optional<Ingredient> findByRecipeIdAndId(String recipeId, String ingredientId);
}
