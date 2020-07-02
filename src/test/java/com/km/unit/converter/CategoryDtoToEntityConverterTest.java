package com.km.unit.converter;

import com.km.converter.CategoryDtoToEntityConverter;
import com.km.dto.CategoryDto;
import com.km.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryDtoToEntityConverterTest {

    private CategoryDtoToEntityConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryDtoToEntityConverter();
    }

    @Test
    void convert() {
        CategoryDto dto = CategoryDto.builder()
                .id("1")
                .description("Category description")
                .build();

        Category result = converter.convert(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getDescription(), result.getDescription());
    }
}