package com.km.unit.converter;

import com.km.converter.IngredientEntityToDtoConverter;
import com.km.converter.UomEntityToDtoConverter;
import com.km.dto.IngredientDto;
import com.km.dto.UnitOfMeasureDto;
import com.km.model.Ingredient;
import com.km.model.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientEntityToDtoConverterTest {

    @Mock
    private UomEntityToDtoConverter uomEntityToDtoConverter;

    @InjectMocks
    private IngredientEntityToDtoConverter converter;

    @Test
    void convert() {
        Ingredient entity = Ingredient.builder()
                .id(1L)
                .description("Ingredient description")
                .amount(new BigDecimal(100))
                .unitOfMeasure(UnitOfMeasure.builder().id(1L).build())
                .build();
        when(uomEntityToDtoConverter.convert(any())).thenReturn(UnitOfMeasureDto.builder().id(1L).build());

        IngredientDto result = converter.convert(entity);

        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getDescription(), result.getDescription());
        assertEquals(entity.getAmount(), result.getAmount());
        assertEquals(entity.getUnitOfMeasure().getId(), result.getUnitOfMeasure().getId());
    }
}