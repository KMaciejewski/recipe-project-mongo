package com.km.converter;

import com.km.dto.UnitOfMeasureDto;
import com.km.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UomDtoToEntityConverterTest {

    private UomDtoToEntityConverter converter;

    @BeforeEach
    void setUp() {
        converter = new UomDtoToEntityConverter();
    }

    @Test
    void convert() {
        UnitOfMeasureDto dto = UnitOfMeasureDto.builder()
                .id("1")
                .description("Unit of measure description")
                .build();

        UnitOfMeasure result = converter.convert(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getDescription(), result.getDescription());
    }
}