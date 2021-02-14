package com.km.repository.reactive;

import com.km.model.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IngredientReactiveRepository extends ReactiveMongoRepository<Ingredient, String> {

    Flux<Ingredient> findAllByRecipeId(String recipeId);

    Mono<Ingredient> findByRecipeIdAndId(String recipeId, String ingredientId);

    Mono<Ingredient> findByDescription(String description);
}
