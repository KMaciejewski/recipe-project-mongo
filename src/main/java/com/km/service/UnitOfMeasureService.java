package com.km.service;

import com.km.dto.UnitOfMeasureDto;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {
    Flux<UnitOfMeasureDto> findAll();
}
