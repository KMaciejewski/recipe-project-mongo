package com.km.controller;

import com.km.dto.RecipeDto;
import com.km.service.RecipeService;
import com.km.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class IndexControllerTest {

    private IndexController indexController;

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    private List<RecipeDto> recipes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        indexController = new IndexController(recipeService);

        recipes = TestUtils.getRecipes(RecipeDto.class);

        Mockito.when(recipeService.findAll()).thenReturn(Flux.fromIterable(recipes));
    }

    @Test
    void getIndexPage() {
        String viewName = indexController.getIndexPage(model);

        assertEquals("index", viewName);
        Mockito.verify(recipeService, Mockito.times(1)).findAll();
        Mockito.verify(model, Mockito.times(1)).addAttribute("recipes", recipes);
    }

    @Test
    void getMvcIndexPage() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}