package com.km.integration.service;

import com.km.dto.IngredientDto;
import com.km.service.IngredientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class IngredientServiceIT {

    @Autowired
    private IngredientService ingredientService;

    @Test
    @Transactional
    void findAllByRecipeId() {
        final Long recipeId = 1L;

        Set<IngredientDto> result = ingredientService.findAllByRecipeId(recipeId);

        final long count = result.stream()
                .filter(dto -> recipeId.equals(dto.getRecipeId()))
                .count();

        assertEquals(count, result.size());
    }

    @Test
    @Transactional
    void findByRecipeIdAndIngredientId() {
        final Long recipeId = 1L;
        final Long ingredientId = 2L;

        IngredientDto result = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);

        assertEquals(recipeId, result.getRecipeId());
        assertEquals(ingredientId, result.getId());
    }

    @Test
    @Transactional
    void findByRecipeIdAndIngredientIdNotFound() {
        final Long recipeId = 99L;
        final Long ingredientId = 999L;

        IngredientDto result = ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId);

        assertDoesNotThrow(() -> new NullPointerException());
        assertNull(result);
    }
}