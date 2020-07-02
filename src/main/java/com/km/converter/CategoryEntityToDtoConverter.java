package com.km.converter;

import com.km.dto.CategoryDto;
import com.km.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToDtoConverter implements Converter<Category, CategoryDto> {

    @Override
    public CategoryDto convert(Category source) {
        return CategoryDto.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
    }
}
