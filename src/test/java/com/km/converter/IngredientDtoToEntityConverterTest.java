package com.km.converter;

import com.km.dto.IngredientDto;
import com.km.dto.UnitOfMeasureDto;
import com.km.model.Ingredient;
import com.km.model.Recipe;
import com.km.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class IngredientDtoToEntityConverterTest {

    private final UomDtoToEntityConverter uomDtoToEntityConverter = new UomDtoToEntityConverter();

    @Mock
    private RecipeRepository recipeRepository;

    private IngredientDtoToEntityConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        converter = new IngredientDtoToEntityConverter(uomDtoToEntityConverter, recipeRepository);
    }

    @Test
    void convert() {
        IngredientDto dto = IngredientDto.builder()
                .id("1")
                .description("Ingredient description")
                .amount(new BigDecimal(100))
                .recipeId("11")
                .unitOfMeasure(UnitOfMeasureDto.builder().id("1").description("Some uom").build())
                .build();
        when(recipeRepository.findById(anyString())).thenReturn(Optional.of(Recipe.builder().id("11").build()));

        Ingredient result = converter.convert(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getAmount(), result.getAmount());
//        assertEquals(dto.getRecipeId(), result.getRecipe().getId());
        assertEquals(dto.getUnitOfMeasure().getId(), result.getUnitOfMeasure().getId());
    }
}