package com.km.service;

import com.km.converter.RecipeDtoToEntityConverter;
import com.km.converter.RecipeEntityToDtoConverter;
import com.km.dto.RecipeDto;
import com.km.exception.NotFoundException;
import com.km.model.Recipe;
import com.km.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeDtoToEntityConverter toEntityConverter;
    private final RecipeEntityToDtoConverter toDtoConverter;

    @Override
    @Transactional
    public Set<RecipeDto> findAll() {
        log.debug("Calling find all from RecipeServiceImpl");
        return StreamSupport
                .stream(recipeRepository.findAll().spliterator(), false)
                .map(toDtoConverter::convert)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public RecipeDto findById(Long id) {
        return toDtoConverter.convert(recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recipe not found. For id value: " + id)));
    }

    @Override
    @Transactional
    public RecipeDto save(RecipeDto dto) {
        Recipe savedRecipe = recipeRepository.save(toEntityConverter.convert(dto));
        log.debug("Saved entity id: {}", savedRecipe.getId());
        return toDtoConverter.convert(savedRecipe);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
