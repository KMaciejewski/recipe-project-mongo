package com.km.controller;

import com.km.dto.IngredientDto;
import com.km.dto.UnitOfMeasureDto;
import com.km.service.IngredientService;
import com.km.service.UnitOfMeasureService;
import com.km.utils.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IngredientControllerTest {

    private static final String RECIPE = "recipe";
    private static final String INGREDIENTS = "ingredients";
    private static final String ONE = "1";
    private static final String TWO = "2";

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private IngredientController ingredientController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(ingredientService, unitOfMeasureService);

        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @SneakyThrows
    @Test
    void findAllByRecipeId() {
        IngredientDto first = IngredientDto.builder().id(ONE).build();
        IngredientDto second = IngredientDto.builder().id(TWO).build();
        when(ingredientService.findAllByRecipeId(anyString())).thenReturn(Flux.just(first, second));

        mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.buildUrl(RECIPE, ONE, INGREDIENTS)))
                .andExpect(status().isOk())
                .andExpect(view().name(INGREDIENTS + "/list"))
                .andExpect(model().attributeExists(INGREDIENTS, "recipeId"));
    }

    @SneakyThrows
    @Test
    void findRecipeIngredient() {
        final IngredientDto dto = IngredientDto.builder().id(ONE).build();
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(Mono.just(dto));

        mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.buildUrl(RECIPE, ONE, INGREDIENTS, ONE)))
                .andExpect(status().isOk())
                .andExpect(view().name(INGREDIENTS + "/show"))
                .andExpect(model().attribute("ingredient", dto));
    }

    @SneakyThrows
    @Test
    void update() {
        IngredientDto dto = IngredientDto.builder().id(ONE).recipeId(ONE).build();
        when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString())).thenReturn(Mono.just(dto));

        List<UnitOfMeasureDto> unitOfMeasureDtos = getUnitOfMeasureDtos();

        mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.buildUrl(RECIPE, ONE, INGREDIENTS, ONE, "update")))
                .andExpect(status().isOk())
                .andExpect(view().name(INGREDIENTS + "/form"))
                .andExpect(model().attribute("ingredient", dto))
                .andExpect(model().attribute("uomList", unitOfMeasureDtos));
    }

    private List<UnitOfMeasureDto> getUnitOfMeasureDtos() {
        List<UnitOfMeasureDto> unitOfMeasureDtos = new ArrayList<>();
        UnitOfMeasureDto teaspoon = UnitOfMeasureDto.builder().id(ONE).description("Teaspoon").build();
        unitOfMeasureDtos.add(teaspoon);
        UnitOfMeasureDto tablespoon = UnitOfMeasureDto.builder().id(ONE).description("Tablespoon").build();
        unitOfMeasureDtos.add(tablespoon);
        UnitOfMeasureDto cup = UnitOfMeasureDto.builder().id(ONE).description("Cup").build();
        unitOfMeasureDtos.add(cup);
        when(unitOfMeasureService.findAll()).thenReturn(Flux.just(teaspoon, tablespoon, cup));
        return unitOfMeasureDtos;
    }

    @SneakyThrows
    @Test
    void saveOrUpdate() {
        final IngredientDto dto = IngredientDto.builder().id(ONE).build();
        when(ingredientService.save(any())).thenReturn(Mono.just(dto));

        mockMvc.perform(MockMvcRequestBuilders.post(TestUtils.buildUrl(RECIPE, ONE, INGREDIENTS)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(format("redirect:/%s/%s/%s/%s", RECIPE, ONE, INGREDIENTS, ONE)));
    }

    @SneakyThrows
    @Test
    void create() {
        final IngredientDto dtoWithRecipeId = IngredientDto.builder().recipeId(ONE).build();
        List<UnitOfMeasureDto> unitOfMeasureDtos = getUnitOfMeasureDtos();

        mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.buildUrl(RECIPE, ONE, INGREDIENTS, "new")))
                .andExpect(status().isOk())
                .andExpect(view().name(INGREDIENTS + "/form"))
                .andExpect(model().attribute("ingredient", dtoWithRecipeId))
                .andExpect(model().attribute("uomList", unitOfMeasureDtos));
    }

    @SneakyThrows
    @Test
    void delete() {
        mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.buildUrl(RECIPE, ONE, INGREDIENTS, ONE, "delete")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/%s/%s/%s", RECIPE, ONE, INGREDIENTS)));

        verify(ingredientService).deleteById(anyString());
    }
}
