package com.km.unit.converter;

import com.km.converter.IngredientEntityToDtoConverter;
import com.km.converter.UomEntityToDtoConverter;
import com.km.dto.IngredientDto;
import com.km.dto.UnitOfMeasureDto;
import com.km.model.Ingredient;
import com.km.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class IngredientEntityToDtoConverterTest {

    @Mock
    private UomEntityToDtoConverter uomEntityToDtoConverter;

    private IngredientEntityToDtoConverter converter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        converter = new IngredientEntityToDtoConverter(uomEntityToDtoConverter);
    }

    @Test
    void convert() {
        Ingredient entity = Ingredient.builder()
                .id("1")
                .description("Ingredient description")
                .amount(new BigDecimal(100))
                .unitOfMeasure(UnitOfMeasure.builder().id("1").build())
                .build();
        when(uomEntityToDtoConverter.convert(any())).thenReturn(UnitOfMeasureDto.builder().id("1").build());

        IngredientDto result = converter.convert(entity);

        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getDescription(), result.getDescription());
        assertEquals(entity.getAmount(), result.getAmount());
        assertEquals(entity.getUnitOfMeasure().getId(), result.getUnitOfMeasure().getId());
    }
}