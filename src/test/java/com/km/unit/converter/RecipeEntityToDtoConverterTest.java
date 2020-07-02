package com.km.unit.converter;

import com.km.converter.CategoryEntityToDtoConverter;
import com.km.converter.IngredientEntityToDtoConverter;
import com.km.converter.NoteEntityToDtoConverter;
import com.km.converter.RecipeEntityToDtoConverter;
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
class RecipeEntityToDtoConverterTest {

    @Mock
    private NoteEntityToDtoConverter noteEntityToDtoConverter;

    @Mock
    private IngredientEntityToDtoConverter ingredientEntityToDtoConverter;

    @Mock
    private CategoryEntityToDtoConverter categoryEntityToDtoConverter;

    @InjectMocks
    private RecipeEntityToDtoConverter converter;

    @Test
    void convert() {
        //given
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.builder().id(1L).description("First ingredient").build());
        ingredients.add(Ingredient.builder().id(2L).description("Second ingredient").build());
        when(ingredientEntityToDtoConverter.convert(any())).thenReturn(
                IngredientDto.builder().id(1L).build(),
                IngredientDto.builder().id(2L).build()
        );

        Set<Category> categories = new HashSet<>();
        categories.add(Category.builder().id(1L).description("First category").build());
        categories.add(Category.builder().id(2L).description("Second category").build());
        when(categoryEntityToDtoConverter.convert(any())).thenReturn(
                CategoryDto.builder().id(1L).description("First category").build(),
                CategoryDto.builder().id(2L).description("Second category").build()
        );

        when(noteEntityToDtoConverter.convert(any())).thenReturn(NoteDto.builder().id(1L).recipeNote("Recipe note").build());

        Recipe entity = Recipe.builder()
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
                .note(Note.builder().id(1L).recipeNote("Recipe note").build())
                .ingredients(ingredients)
                .categories(categories)
                .build();

        //when
        RecipeDto result = converter.convert(entity);

        //then
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getDescription(), result.getDescription());
        assertEquals(entity.getPrepTime(), result.getPrepTime());
        assertEquals(entity.getCookTime(), result.getCookTime());
        assertEquals(entity.getServings(), result.getServings());
        assertEquals(entity.getSource(), result.getSource());
        assertEquals(entity.getUrl(), result.getUrl());
        assertEquals(entity.getDirection(), result.getDirection());
        assertEquals(entity.getDifficulty(), result.getDifficulty());
        assertEquals(entity.getImage(), result.getImage());
        assertEquals(entity.getNote().getRecipeNote(), result.getNote().getRecipeNote());
        assertEquals(entity.getIngredients().size(), result.getIngredients().size());
        assertEquals(entity.getCategories().size(), result.getCategories().size());
    }
}