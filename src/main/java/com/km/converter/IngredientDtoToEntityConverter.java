package com.km.converter;

import com.km.dto.IngredientDto;
import com.km.model.Ingredient;
import com.km.model.Recipe;
import com.km.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IngredientDtoToEntityConverter implements Converter<IngredientDto, Ingredient> {

    private final UomDtoToEntityConverter uomDtoToEntityConverter;
    private final RecipeRepository recipeRepository;

    @Override
    public Ingredient convert(IngredientDto source) {
        return Ingredient.builder()
                .id(source.getId())
                .description(source.getDescription())
                .amount(source.getAmount())
                .recipe(getRecipe(source.getRecipeId()))
                .unitOfMeasure(uomDtoToEntityConverter.convert(source.getUnitOfMeasure()))
                .build();
    }

    private Recipe getRecipe(Long recipeId) {
        return Optional.ofNullable(recipeId)
                .flatMap(id -> recipeRepository.findById(id))
                .orElse(null);
    }
}
