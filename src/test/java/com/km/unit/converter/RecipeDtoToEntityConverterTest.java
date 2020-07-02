package com.km.unit.converter;

import com.km.converter.CategoryDtoToEntityConverter;
import com.km.converter.IngredientDtoToEntityConverter;
import com.km.converter.NoteDtoToEntityConverter;
import com.km.converter.RecipeDtoToEntityConverter;
import com.km.dto.CategoryDto;
import com.km.dto.IngredientDto;
import com.km.dto.NoteDto;
import com.km.dto.RecipeDto;
import com.km.model.Category;
import com.km.model.Difficulty;
import com.km.model.Ingredient;
import com.km.model.Note;
import com.km.model.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeDtoToEntityConverterTest {

    @Mock
    private NoteDtoToEntityConverter noteDtoToEntityConverter;

    @Mock
    private IngredientDtoToEntityConverter ingredientDtoToEntityConverter;

    @Mock
    private CategoryDtoToEntityConverter categoryDtoToEntityConverter;

    @InjectMocks
    private RecipeDtoToEntityConverter converter;

    @Test
    void convert() {
        //given
        Set<IngredientDto> ingredients = new HashSet<>();
        ingredients.add(IngredientDto.builder().id(1L).build());
        ingredients.add(IngredientDto.builder().id(2L).build());
        when(ingredientDtoToEntityConverter.convert(any())).thenReturn(
                Ingredient.builder().id(1L).build(),
                Ingredient.builder().id(2L).build()
        );

        Set<CategoryDto> categories = new HashSet<>();
        categories.add(CategoryDto.builder().id(1L).build());
        categories.add(CategoryDto.builder().id(2L).build());
        when(categoryDtoToEntityConverter.convert(any())).thenReturn(
                Category.builder().id(1L).description("First category").build(),
                Category.builder().id(2L).description("Second category").build()
        );

        when(noteDtoToEntityConverter.convert(any())).thenReturn(Note.builder().id(1L).recipeNote("Recipe note").build());

        RecipeDto dto = RecipeDto.builder()
                .id(1L)
                .description("Recipe description")
                .prepTime(10)
                .cookTime(5)
                .servings(3)
                .source("Simply recipes")
                .url("www.example.com")
                .direction("Test direction")
                .difficulty(Difficulty.EASY)
                .image(new Byte[100])
                .note(NoteDto.builder().id(1L).recipeNote("Recipe note").build())
                .ingredients(ingredients)
                .categories(categories)
                .build();

        //when
        Recipe result = converter.convert(dto);

        //then
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getPrepTime(), result.getPrepTime());
        assertEquals(dto.getCookTime(), result.getCookTime());
        assertEquals(dto.getServings(), result.getServings());
        assertEquals(dto.getSource(), result.getSource());
        assertEquals(dto.getUrl(), result.getUrl());
        assertEquals(dto.getDirection(), result.getDirection());
        assertEquals(dto.getDifficulty(), result.getDifficulty());
        assertEquals(dto.getImage(), result.getImage());
        assertEquals(dto.getNote().getRecipeNote(), result.getNote().getRecipeNote());
        assertEquals(dto.getIngredients().size(), result.getIngredients().size());
        assertEquals(dto.getCategories().size(), result.getCategories().size());
    }
}