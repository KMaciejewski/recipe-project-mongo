package com.km.model;

import com.km.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        String id = "4";

        category.setId(id);

        assertEquals(id, category.getId());
    }

    @Test
    void getDescription() {
        String description = "Some description";

        category.setDescription(description);

        assertEquals(description, category.getDescription());
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipes = TestUtils.getRecipes(Recipe.class);

        category.setRecipes(recipes);

        assertEquals(recipes, category.getRecipes());
    }
}