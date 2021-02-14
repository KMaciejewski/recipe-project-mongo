package com.km.converter;

import com.km.dto.CategoryDto;
import com.km.dto.IngredientDto;
import com.km.dto.NoteDto;
import com.km.dto.RecipeDto;
import com.km.model.Category;
import com.km.model.Difficulty;
import com.km.model.Ingredient;
import com.km.model.Note;
import com.km.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RecipeEntityToDtoConverterTest {

    @Mock
    private NoteEntityToDtoConverter noteEntityToDtoConverter;

    @Mock
    private IngredientEntityToDtoConverter ingredientEntityToDtoConverter;

    @Mock
    private CategoryEntityToDtoConverter categoryEntityToDtoConverter;

    private RecipeEntityToDtoConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        converter = new RecipeEntityToDtoConverter(
                noteEntityToDtoConverter,
                ingredientEntityToDtoConverter,
                categoryEntityToDtoConverter
        );
    }

    @Test
    void convert() {
        //given
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(Ingredient.builder().id("1").description("First ingredient").build());
        ingredients.add(Ingredient.builder().id("2").description("Second ingredient").build());
        when(ingredientEntityToDtoConverter.convert(any())).thenReturn(
                IngredientDto.builder().id("1").build(),
                IngredientDto.builder().id("2").build()
        );

        Set<Category> categories = new HashSet<>();
        categories.add(Category.builder().id("1").description("First category").build());
        categories.add(Category.builder().id("2").description("Second category").build());
        when(categoryEntityToDtoConverter.convert(any())).thenReturn(
                CategoryDto.builder().id("1").description("First category").build(),
                CategoryDto.builder().id("2").description("Second category").build()
        );

        when(noteEntityToDtoConverter.convert(any())).thenReturn(NoteDto.builder().id("1").recipeNote("Recipe note").build());

        Recipe entity = Recipe.builder()
                .id("1")
                .description("Recipe description")
                .prepTime(10)
                .cookTime(5)
                .servings(3)
                .source("Simply recipes")
                .url("www.example.com")
                .direction("Test direction")
                .difficulty(Difficulty.EASY)
                .image(new Byte[100])
                .note(Note.builder().id("1").recipeNote("Recipe note").build())
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