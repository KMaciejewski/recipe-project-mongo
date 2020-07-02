package com.km.unit.converter;

import com.km.converter.IngredientDtoToEntityConverter;
import com.km.converter.UomDtoToEntityConverter;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class IngredientDtoToEntityConverterTest {

    private UomDtoToEntityConverter uomDtoToEntityConverter = new UomDtoToEntityConverter();

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
                .id(1L)
                .description("Ingredient description")
                .amount(new BigDecimal(100))
                .recipeId(11L)
                .unitOfMeasure(UnitOfMeasureDto.builder().id(1L).description("Some uom").build())
                .build();
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(Recipe.builder().id(11L).build()));

        Ingredient result = converter.convert(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getRecipeId(), result.getRecipe().getId());
        assertEquals(dto.getUnitOfMeasure().getId(), result.getUnitOfMeasure().getId());
    }
}