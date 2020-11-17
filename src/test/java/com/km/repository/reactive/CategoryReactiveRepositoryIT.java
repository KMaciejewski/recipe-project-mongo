package com.km.repository.reactive;

import com.km.model.Category;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testFindByDescription() {
        String description = "First category";
        Category category = new Category();
        category.setDescription(description);

        categoryReactiveRepository.save(category).block();

        Category result = categoryReactiveRepository.findByDescription(description).block();

        Assert.assertNotNull(result.getId());
    }
}
