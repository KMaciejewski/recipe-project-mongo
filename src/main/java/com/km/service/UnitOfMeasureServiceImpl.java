package com.km.service;

import com.km.converter.UomEntityToDtoConverter;
import com.km.dto.UnitOfMeasureDto;
import com.km.repository.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UomEntityToDtoConverter toDtoConverter;

    @Override
    public Set<UnitOfMeasureDto> findAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(toDtoConverter::convert)
                .collect(toSet());
    }
}
