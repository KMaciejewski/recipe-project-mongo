package com.km.service;

import com.km.converter.RecipeDtoToEntityConverter;
import com.km.converter.RecipeEntityToDtoConverter;
import com.km.dto.RecipeDto;
import com.km.repository.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeDtoToEntityConverter toEntityConverter;
    private final RecipeEntityToDtoConverter toDtoConverter;

    @Override
    public Flux<RecipeDto> findAll() {
        log.debug("Calling find all from RecipeServiceImpl");
        return recipeReactiveRepository.findAll()
                .map(toDtoConverter::convert);
    }

    @Override
    public Mono<RecipeDto> findById(String id) {
        return recipeReactiveRepository.findById(id)
                .map(toDtoConverter::convert);
    }

    @Override
    public Mono<RecipeDto> save(RecipeDto dto) {
        return recipeReactiveRepository.save(toEntityConverter.convert(dto))
                .map(toDtoConverter::convert);
    }

    @Override
    public void deleteById(String id) {
        recipeReactiveRepository.deleteById(id).block();
    }
}
