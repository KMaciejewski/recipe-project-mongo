package com.km.converter;

import com.km.converter.CategoryEntityToDtoConverter;
import com.km.dto.CategoryDto;
import com.km.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryEntityToDtoConverterTest {

    private CategoryEntityToDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryEntityToDtoConverter();
    }

    @Test
    void convert() {
        Category entity = Category.builder()
                .id("1")
                .description("Category description")
                .build();

        CategoryDto result = converter.convert(entity);

        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getDescription(), result.getDescription());
    }
}