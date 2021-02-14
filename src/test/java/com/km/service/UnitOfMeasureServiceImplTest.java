package com.km.service;

import com.km.converter.UomEntityToDtoConverter;
import com.km.dto.UnitOfMeasureDto;
import com.km.model.UnitOfMeasure;
import com.km.repository.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    private UomEntityToDtoConverter toDtoConverter;

    private UnitOfMeasureServiceImpl unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        toDtoConverter = new UomEntityToDtoConverter();

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, toDtoConverter);
    }

    @Test
    void findAll() {
        List<UnitOfMeasure> uomList = new ArrayList<>();
        UnitOfMeasure teaspoon = UnitOfMeasure.builder().id("1").description("Teaspoon").build();
        uomList.add(teaspoon);
        UnitOfMeasure cup = UnitOfMeasure.builder().id("2").description("Cup").build();
        uomList.add(cup);
        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(teaspoon, cup));

        Flux<UnitOfMeasureDto> result = unitOfMeasureService.findAll();

        verify(unitOfMeasureReactiveRepository).findAll();
        assertEquals(uomList.size(), result.count().block().longValue());
    }
}