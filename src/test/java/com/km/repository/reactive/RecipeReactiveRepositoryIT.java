package com.km.repository.reactive;

import com.km.model.Recipe;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {

    @Autowired
    private RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testRecipeSave() {
        Recipe recipe = new Recipe();
        recipe.setDescription("Yummy");

        recipeReactiveRepository.save(recipe).block();

        Long count = recipeReactiveRepository.count().block();

        Assert.assertEquals(Long.valueOf(1L), count);
    }
}
