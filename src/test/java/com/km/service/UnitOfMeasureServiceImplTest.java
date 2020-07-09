package com.km.service;

import com.km.converter.UomEntityToDtoConverter;
import com.km.dto.UnitOfMeasureDto;
import com.km.model.UnitOfMeasure;
import com.km.repository.UnitOfMeasureRepository;
import com.km.service.UnitOfMeasureServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private UomEntityToDtoConverter toDtoConverter;

    private UnitOfMeasureServiceImpl unitOfMeasureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        toDtoConverter = new UomEntityToDtoConverter();

        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, toDtoConverter);
    }

    @Test
    void findAll() {
        List<UnitOfMeasure> uomList = new ArrayList<>();
        uomList.add(UnitOfMeasure.builder().id("1").description("Teaspoon").build());
        uomList.add(UnitOfMeasure.builder().id("2").description("Cup").build());
        when(unitOfMeasureRepository.findAll()).thenReturn(uomList);

        Set<UnitOfMeasureDto> result = unitOfMeasureService.findAll();

        verify(unitOfMeasureRepository).findAll();
        assertEquals(uomList.size(), result.size());
    }
}