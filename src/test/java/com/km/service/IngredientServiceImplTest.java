package com.km.service;

import com.km.converter.IngredientDtoToEntityConverter;
import com.km.converter.IngredientEntityToDtoConverter;
import com.km.dto.IngredientDto;
import com.km.model.Ingredient;
import com.km.model.Recipe;
import com.km.repository.reactive.IngredientReactiveRepository;
import com.km.repository.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
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
    private IngredientReactiveRepository ingredientReactiveRepository;

    @Mock
    private IngredientEntityToDtoConverter toDtoConverter;

    @Mock
    private IngredientDtoToEntityConverter toEntityConverter;

    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;

    private IngredientServiceImpl ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(
                ingredientReactiveRepository, toDtoConverter, toEntityConverter, recipeReactiveRepository
        );
    }

    @Test
    void findAllByRecipeId() {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(getIngredientEntity(ONE));
        ingredients.add(getIngredientEntity(TWO));
        Recipe recipe = Recipe.builder()
                .id(ONE)
                .ingredients(ingredients)
                .build();

        when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
        when(toDtoConverter.convert(any())).thenReturn(getIngredientDto(ONE), getIngredientDto(TWO));

        Flux<IngredientDto> result = ingredientService.findAllByRecipeId(ONE);

        assertEquals(2, result.count().block().longValue());
    }

    @Test
    void findByRecipeIdAndId() {
        when(ingredientReactiveRepository.findByRecipeIdAndId(anyString(), any()))
                .thenReturn(Mono.just((getIngredientEntity(ONE))));
        when(toDtoConverter.convert(any())).thenReturn(getIngredientDto(ONE));

        Mono<IngredientDto> result = ingredientService.findByRecipeIdAndIngredientId(ONE, ONE);

        verify(ingredientReactiveRepository).findByRecipeIdAndId(anyString(), anyString());
        assertEquals(ONE, result.block().getId());
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
        Ingredient entity = getIngredientEntity(ONE);
        when(toEntityConverter.convert(any())).thenReturn(entity);
        when(ingredientReactiveRepository.save(any())).thenReturn(Mono.just(entity));
        when(toDtoConverter.convert(any())).thenReturn(dto);

        ingredientService.save(dto);

        verify(toEntityConverter).convert(any());
        verify(ingredientReactiveRepository).save(any());
    }

    @Test
    void deleteById() {
        when(ingredientReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        ingredientService.deleteById(ONE);

        verify(ingredientReactiveRepository, times(1)).deleteById(anyString());
    }
}