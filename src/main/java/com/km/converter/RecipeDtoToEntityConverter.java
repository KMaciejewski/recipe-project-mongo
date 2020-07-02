package com.km.converter;

import com.km.dto.CategoryDto;
import com.km.dto.IngredientDto;
import com.km.dto.RecipeDto;
import com.km.model.Category;
import com.km.model.Ingredient;
import com.km.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class RecipeDtoToEntityConverter implements Converter<RecipeDto, Recipe> {

    private final NoteDtoToEntityConverter noteConverter;
    private final IngredientDtoToEntityConverter ingredientConverter;
    private final CategoryDtoToEntityConverter categoryConverter;

    @Override
    public Recipe convert(RecipeDto source) {
        return Recipe.builder()
                .id(source.getId())
                .description(source.getDescription())
                .prepTime(source.getPrepTime())
                .cookTime(source.getCookTime())
                .servings(source.getServings())
                .source(source.getSource())
                .url(source.getUrl())
                .direction(source.getDirection())
                .difficulty(source.getDifficulty())
                .image(source.getImage())
                .note(noteConverter.convert(source.getNote()))
                .ingredients(convertIngredients(source.getIngredients()))
                .categories(convertCategories(source.getCategories()))
                .build();
    }

    private Set<Ingredient> convertIngredients(Set<IngredientDto> ingredients) {
        return ingredients.stream()
                .map(source -> ingredientConverter.convert(source))
                .collect(toSet());
    }

    private Set<Category> convertCategories(Set<CategoryDto> categories) {
        return categories.stream()
                .map(category -> categoryConverter.convert(category))
                .collect(toSet());
    }
}
