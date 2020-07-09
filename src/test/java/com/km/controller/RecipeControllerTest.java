package com.km.controller;

import com.km.dto.RecipeDto;
import com.km.exception.NotFoundException;
import com.km.service.RecipeService;
import com.km.utils.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class RecipeControllerTest {

    private static final String ID = "1";
    private static final String BASE_URL = "/recipes/";

    @Mock
    private RecipeService recipeService;

    private RecipeController recipeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        recipeController = new RecipeController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @SneakyThrows
    @Test
    void showById() {
        when(recipeService.findById(ID)).thenReturn(TestUtils.getById(RecipeDto.class, ID));

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + ID))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/show"))
                .andExpect(model().attribute("recipe", recipeService.findById(ID)));
    }

    @SneakyThrows
    @Test
    void showByIdNotFound() {
        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + ID))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"))
                .andExpect(model().attributeExists("exception"));
    }

    @SneakyThrows
    @Test
    void showByIdNumberFormatException() {
        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "abc"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"))
                .andExpect(model().attributeExists("exception"));
    }

    @SneakyThrows
    @Test
    void create() {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/form"))
                .andExpect(model().attribute("recipe", new RecipeDto()));
    }

    @SneakyThrows
    @Test
    void update() {
        final RecipeDto dto = TestUtils.getById(RecipeDto.class, ID);
        when(recipeService.findById(ID)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + ID + "/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/form"))
                .andExpect(model().attribute("recipe", dto));
    }

    @SneakyThrows
    @Test
    void saveOrUpdate() {
        RecipeDto dto = RecipeDto.builder().id("1").build();
        when(recipeService.save(any())).thenReturn(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", ID)
                        .param("description", "Some description")
                        .param("direction", "some direction"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + BASE_URL + ID));
    }

    @Test
    public void saveOrUpdateValidationFail() throws Exception {
        RecipeDto dto = new RecipeDto();
        dto.setId("2");

        mockMvc.perform(
                MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/form"));
    }

    @SneakyThrows
    @Test
    void delete() {
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + ID + "/delete"))
                .andExpect(status().is3xxRedirection());

        verify(recipeService).deleteById(anyString());
    }
}