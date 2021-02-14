package com.km.service;

import com.km.converter.IngredientDtoToEntityConverter;
import com.km.converter.IngredientEntityToDtoConverter;
import com.km.dto.IngredientDto;
import com.km.model.Ingredient;
import com.km.repository.reactive.IngredientReactiveRepository;
import com.km.repository.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientReactiveRepository ingredientReactiveRepository;
    private final IngredientEntityToDtoConverter toDtoConverter;
    private final IngredientDtoToEntityConverter toEntityConverter;
    private final RecipeReactiveRepository recipeReactiveRepository;

    @Override
    public Flux<IngredientDto> findAllByRecipeId(String recipeId) {
        return Flux.fromIterable(
                recipeReactiveRepository.findById(recipeId)
                        .block()
                        .getIngredients())
                .map(toDtoConverter::convert);
    }

    @Override
    public Mono<IngredientDto> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return ingredientReactiveRepository.findByRecipeIdAndId(recipeId, ingredientId)
                .map(toDtoConverter::convert);
    }

    @Override
    public Mono<IngredientDto> save(IngredientDto dto) {
        return ingredientReactiveRepository.save(toEntityConverter.convert(dto))
                .map(toDtoConverter::convert);
    }

    @Override
    public void deleteById(String ingredientId) {
        ingredientReactiveRepository.deleteById(ingredientId).block();
    }
}
