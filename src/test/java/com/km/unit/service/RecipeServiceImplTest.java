package com.km.unit.service;

import com.km.converter.RecipeDtoToEntityConverter;
import com.km.converter.RecipeEntityToDtoConverter;
import com.km.dto.RecipeDto;
import com.km.exception.NotFoundException;
import com.km.model.Recipe;
import com.km.repository.RecipeRepository;
import com.km.service.RecipeService;
import com.km.service.RecipeServiceImpl;
import com.km.unit.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RecipeServiceImplTest {

    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeDtoToEntityConverter toEntityConverter;

    @Mock
    private RecipeEntityToDtoConverter toDtoConverter;

    private Set<Recipe> entities;
    private Set<RecipeDto> dtos;
    private final String ID = "1";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeService = new RecipeServiceImpl(recipeRepository, toEntityConverter, toDtoConverter);

        entities = TestUtils.getRecipes(Recipe.class);
        dtos = TestUtils.getRecipes(RecipeDto.class);

        when(recipeRepository.findAll()).thenReturn(entities);
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

        Set<RecipeDto> result = recipeService.findAll();

        assertEquals(dtos, result);
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
        Mockito.verify(toDtoConverter, Mockito.times(dtos.size())).convert(any());
    }

    @Test
    void findById() {
        final Recipe recipe = TestUtils.getById(Recipe.class, ID);
        when(recipeRepository.findById(ID)).thenReturn(Optional.of(recipe));
        when(toDtoConverter.convert(recipe)).thenReturn(TestUtils.getById(RecipeDto.class, ID));

        RecipeDto result = recipeService.findById(ID);

        assertEquals(ID, result.getId());
    }

    @Test
    void findByIdNotFound() {
        assertThrows(NotFoundException.class,
                () -> recipeService.findById(anyString()), "Entity not found");
    }

    @Test
    void save() {
        Recipe entity = Recipe.builder().id("1").build();
        RecipeDto dto = RecipeDto.builder().id("1").build();

        when(toEntityConverter.convert(dto)).thenReturn(entity);
        when(recipeRepository.save(entity)).thenReturn(entity);
        when(toDtoConverter.convert(entity)).thenReturn(dto);

        recipeService.save(dto);

        verify(toEntityConverter).convert(dto);
        verify(recipeRepository).save(entity);
        verify(toDtoConverter).convert(entity);
    }

    @Test
    void deleteById() {
        recipeService.deleteById(ID);

        verify(recipeRepository).deleteById(anyString());
    }
}