package com.km.service;

import com.km.converter.UomEntityToDtoConverter;
import com.km.dto.UnitOfMeasureDto;
import com.km.repository.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final UomEntityToDtoConverter toDtoConverter;

    @Override
    public Flux<UnitOfMeasureDto> findAll() {
        return unitOfMeasureReactiveRepository.findAll()
                .map(toDtoConverter::convert);
    }
}
