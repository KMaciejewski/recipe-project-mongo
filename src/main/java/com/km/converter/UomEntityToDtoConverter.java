package com.km.converter;

import com.km.dto.UnitOfMeasureDto;
import com.km.model.UnitOfMeasure;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UomEntityToDtoConverter implements Converter<UnitOfMeasure, UnitOfMeasureDto> {

    @Override
    public UnitOfMeasureDto convert(UnitOfMeasure source) {
        return UnitOfMeasureDto.builder()
                .id(source.getId())
                .description(source.getDescription())
                .build();
    }
}
