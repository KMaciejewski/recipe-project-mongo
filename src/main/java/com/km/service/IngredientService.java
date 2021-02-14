package com.km.service;

import com.km.dto.IngredientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Flux<IngredientDto> findAllByRecipeId(String recipeId);

    Mono<IngredientDto> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientDto> save(IngredientDto dto);

    void deleteById(String ingredientId);
}
