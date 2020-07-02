package com.km.service;

import com.km.dto.IngredientDto;

import java.util.Set;

public interface IngredientService {

    Set<IngredientDto> findAllByRecipeId(String recipeId);

    IngredientDto findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    IngredientDto save(IngredientDto dto);

    void deleteById(String ingredientId);
}
