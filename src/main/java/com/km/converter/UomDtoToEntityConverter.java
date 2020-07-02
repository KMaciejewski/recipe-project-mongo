package com.km.converter;

import com.km.dto.UnitOfMeasureDto;
import com.km.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UomDtoToEntityConverter implements Converter<UnitOfMeasureDto, UnitOfMeasure> {

    @Override
    public UnitOfMeasure convert(UnitOfMeasureDto source) {
        return UnitOfMeasure.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
    }
}
