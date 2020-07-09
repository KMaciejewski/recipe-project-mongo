package com.km.service;

import com.km.converter.IngredientDtoToEntityConverter;
import com.km.converter.IngredientEntityToDtoConverter;
import com.km.dto.IngredientDto;
import com.km.model.Ingredient;
import com.km.repository.IngredientRepository;
import com.km.service.IngredientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class IngredientServiceImplTest {

    private static final String ONE = "1", TWO = "2";

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientEntityToDtoConverter toDtoConverter;

    @Mock
    private IngredientDtoToEntityConverter toEntityConverter;

    private IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(
                ingredientRepository, toDtoConverter, toEntityConverter
        );
    }

    @Test
    void findAllByRecipeId() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(getIngredientEntity(ONE));
        ingredients.add(getIngredientEntity(TWO));

        when(ingredientRepository.findAllByRecipeId(anyString())).thenReturn(ingredients);
        when(toDtoConverter.convert(any())).thenReturn(getIngredientDto(ONE), getIngredientDto(TWO));

        Set<IngredientDto> result = ingredientService.findAllByRecipeId(ONE);

        verify(ingredientRepository).findAllByRecipeId(anyString());
        verify(toDtoConverter, times(ingredients.size())).convert(any());
        assertEquals(2, result.size());
    }

    @Test
    void findByRecipeIdAndId() {
        when(ingredientRepository.findByRecipeIdAndId(anyString(), any()))
                .thenReturn(Optional.of(getIngredientEntity(ONE)));
        when(toDtoConverter.convert(any())).thenReturn(getIngredientDto(ONE));

        IngredientDto result = ingredientService.findByRecipeIdAndIngredientId(ONE, ONE);

        verify(ingredientRepository).findByRecipeIdAndId(anyString(), anyString());
        verify(toDtoConverter).convert(any());
        assertEquals(ONE, result.getId());
    }

    private Ingredient getIngredientEntity(String id) {
        return Ingredient.builder().id(id).build();
    }

    private IngredientDto getIngredientDto(String id) {
        return IngredientDto.builder().id(id).build();
    }

    @Test
    void save() {
        final IngredientDto dto = getIngredientDto(ONE);

        ingredientService.save(dto);

        verify(toEntityConverter).convert(any());
        verify(ingredientRepository).save(any());
        verify(toDtoConverter).convert(any());
    }

    @Test
    void deleteById() {
        ingredientService.deleteById(ONE);

        verify(ingredientRepository).deleteById(anyString());
    }
}