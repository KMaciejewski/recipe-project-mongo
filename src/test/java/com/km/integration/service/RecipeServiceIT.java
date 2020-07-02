package com.km.integration.service;

import com.km.converter.RecipeEntityToDtoConverter;
import com.km.dto.RecipeDto;
import com.km.model.Recipe;
import com.km.repository.RecipeRepository;
import com.km.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Using @DataJpaTest in this case causing NoSuchBeanDefinitionException.
 * {@code @SpringBootTest} annotation brings up whole context with Spring configuration.
 */
@SpringBootTest
class RecipeServiceIT {

    private final String NEW_DESCRIPTION = "New description";

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeEntityToDtoConverter toDtoConverter;

    /**
     * {@code @Transactional} annotation used to avoid LazyInitializationException
     * for ingredients and categories properties
     */
    @Transactional
    @Test
    void save() {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();
        Recipe recipe = recipes.iterator().next();
        RecipeDto dto = toDtoConverter.convert(recipe);

        //when
        dto.setDescription(NEW_DESCRIPTION);
        RecipeDto savedDto = recipeService.save(dto);

        //then
        assertEquals(NEW_DESCRIPTION, savedDto.getDescription());
        assertEquals(dto.getId(), savedDto.getId());
        assertEquals(dto.getIngredients().size(), savedDto.getIngredients().size());
        assertEquals(dto.getCategories().size(), savedDto.getCategories().size());
    }
}