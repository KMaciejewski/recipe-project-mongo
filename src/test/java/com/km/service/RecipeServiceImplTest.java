package com.km.service;

import com.km.converter.RecipeDtoToEntityConverter;
import com.km.converter.RecipeEntityToDtoConverter;
import com.km.dto.RecipeDto;
import com.km.model.Recipe;
import com.km.repository.reactive.RecipeReactiveRepository;
import com.km.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {

    private RecipeService recipeService;

    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    private RecipeDtoToEntityConverter toEntityConverter;

    @Mock
    private RecipeEntityToDtoConverter toDtoConverter;

    private List<Recipe> entities;
    private List<RecipeDto> dtos;
    private final String ID = "1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeReactiveRepository, toEntityConverter, toDtoConverter);

        entities = TestUtils.getRecipes(Recipe.class);
        dtos = TestUtils.getRecipes(RecipeDto.class);

        when(recipeReactiveRepository.findAll()).thenReturn(Flux.fromIterable(entities));
    }

    @Test
    void findAll() {
        when(toDtoConverter.convert(any())).thenReturn(
                TestUtils.getById(RecipeDto.class, "1"),
                TestUtils.getById(RecipeDto.class, "2"),
                TestUtils.getById(RecipeDto.class, "3"),
                TestUtils.getById(RecipeDto.class, "4"),
                TestUtils.getById(RecipeDto.class, "5")
        );

        Flux<RecipeDto> result = recipeService.findAll();

        assertEquals(dtos, result.collectList().block());
        Mockito.verify(recipeReactiveRepository, Mockito.times(1)).findAll();
        Mockito.verify(toDtoConverter, Mockito.times(dtos.size())).convert(any());
    }

    @Test
    void findById() {
        final Recipe recipe = TestUtils.getById(Recipe.class, ID);
        when(recipeReactiveRepository.findById(ID)).thenReturn(Mono.just(recipe));
        when(toDtoConverter.convert(recipe)).thenReturn(TestUtils.getById(RecipeDto.class, ID));

        Mono<RecipeDto> result = recipeService.findById(ID);

        assertEquals(ID, result.block().getId());
    }

    @Test
    void save() {
        Recipe entity = Recipe.builder().id("1").build();
        RecipeDto dto = RecipeDto.builder().id("1").build();

        when(toEntityConverter.convert(dto)).thenReturn(entity);
        when(recipeReactiveRepository.save(entity)).thenReturn(Mono.just(entity));
        when(toDtoConverter.convert(entity)).thenReturn(dto);

        recipeService.save(dto);

        verify(toEntityConverter).convert(dto);
        verify(recipeReactiveRepository).save(entity);
    }

    @Test
    void deleteById() {
        when(recipeReactiveRepository.deleteById(anyString())).thenReturn(Mono.empty());

        recipeService.deleteById(ID);

        verify(recipeReactiveRepository, times(1)).deleteById(anyString());
    }
}