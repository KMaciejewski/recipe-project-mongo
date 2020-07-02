package com.km.unit.converter;

import com.km.converter.UomEntityToDtoConverter;
import com.km.dto.UnitOfMeasureDto;
import com.km.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UomEntityToDtoConverterTest {

    private UomEntityToDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new UomEntityToDtoConverter();
    }

    @Test
    void convert() {
        UnitOfMeasure entity = UnitOfMeasure.builder()
                .id(1L)
                .description("Unit of measure description")
                .build();

        UnitOfMeasureDto result = converter.convert(entity);

        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getDescription(), result.getDescription());
    }
}