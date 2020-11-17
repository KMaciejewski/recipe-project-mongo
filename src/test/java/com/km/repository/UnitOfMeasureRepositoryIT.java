package com.km.repository;

import com.km.bootstrap.RecipeBootstrap;
import com.km.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    private RecipeBootstrap recipeBootstrap;

    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll();
        unitOfMeasureRepository.deleteAll();
        categoryRepository.deleteAll();

        recipeBootstrap = new RecipeBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository, ingredientRepository);
        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    void findByDescription() {
        Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }
}