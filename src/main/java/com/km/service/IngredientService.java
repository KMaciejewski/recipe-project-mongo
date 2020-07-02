package com.km.service;

import com.km.dto.IngredientDto;

import java.util.Set;

public interface IngredientService {

    Set<IngredientDto> findAllByRecipeId(Long recipeId);

    IngredientDto findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientDto save(IngredientDto dto);

    void deleteById(Long ingredientId);
}
