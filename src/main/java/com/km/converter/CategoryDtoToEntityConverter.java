package com.km.converter;

import com.km.dto.CategoryDto;
import com.km.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoToEntityConverter implements Converter<CategoryDto, Category> {

    @Override
    public Category convert(CategoryDto source) {
        return Category.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
    }
}
