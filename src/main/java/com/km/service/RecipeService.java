package com.km.service;

import com.km.dto.RecipeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<RecipeDto> findAll();

    Mono<RecipeDto> findById(String id);

    Mono<RecipeDto> save(RecipeDto dto);

    void deleteById(String id);
}
