package com.km.unit.service;

import com.km.converter.IngredientDtoToEntityConverter;
import com.km.converter.IngredientEntityToDtoConverter;
import com.km.dto.IngredientDto;
import com.km.model.Ingredient;
import com.km.repository.IngredientRepository;
import com.km.service.IngredientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private static final Long ONE = 1L, TWO = 2L;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientEntityToDtoConverter toDtoConverter;

    @Mock
    private IngredientDtoToEntityConverter toEntityConverter;

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Test
    void findAllByRecipeId() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(getIngredientEntity(ONE));
        ingredients.add(getIngredientEntity(TWO));

        when(ingredientRepository.findAllByRecipeId(anyLong())).thenReturn(ingredients);
        when(toDtoConverter.convert(any())).thenReturn(getIngredientDto(ONE), getIngredientDto(TWO));

        Set<IngredientDto> result = ingredientService.findAllByRecipeId(ONE);

        verify(ingredientRepository).findAllByRecipeId(anyLong());
        verify(toDtoConverter, times(ingredients.size())).convert(any());
        assertEquals(TWO, result.size());
    }

    @Test
    void findByRecipeIdAndId() {
        when(ingredientRepository.findByRecipeIdAndId(anyLong(), any()))
                .thenReturn(Optional.of(getIngredientEntity(ONE)));
        when(toDtoConverter.convert(any())).thenReturn(getIngredientDto(ONE));

        IngredientDto result = ingredientService.findByRecipeIdAndIngredientId(ONE, ONE);

        verify(ingredientRepository).findByRecipeIdAndId(anyLong(), anyLong());
        verify(toDtoConverter).convert(any());
        assertEquals(ONE, result.getId());
    }

    private Ingredient getIngredientEntity(Long one) {
        return Ingredient.builder().id(one).build();
    }

    private IngredientDto getIngredientDto(Long one) {
        return IngredientDto.builder().id(one).build();
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

        verify(ingredientRepository).deleteById(anyLong());
    }
}