package com.km.repository.reactive;

import com.km.model.Recipe;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {
}
