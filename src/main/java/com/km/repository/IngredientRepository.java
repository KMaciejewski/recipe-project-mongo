package com.km.repository;

import com.km.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

    Set<Ingredient> findAllByRecipeId(Long recipeId);

    Optional<Ingredient> findByRecipeIdAndId(Long recipeId, Long ingredientId);
}
