package com.km.converter;

import com.km.dto.IngredientDto;
import com.km.model.Ingredient;
import com.km.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IngredientEntityToDtoConverter implements Converter<Ingredient, IngredientDto> {

    private final UomEntityToDtoConverter uomEntityToDtoConverter;

    @Override
    public IngredientDto convert(Ingredient source) {
        return IngredientDto.builder()
                .id(source.getId())
                .description(source.getDescription())
                .amount(source.getAmount())
                .recipeId(getRecipeId(source.getRecipe()))
                .unitOfMeasure(uomEntityToDtoConverter.convert(source.getUnitOfMeasure()))
                .build();
    }

    private String getRecipeId(Recipe recipe) {
        return Optional.ofNullable(recipe)
                .map(Recipe::getId)
                .orElse(null);
    }
}
