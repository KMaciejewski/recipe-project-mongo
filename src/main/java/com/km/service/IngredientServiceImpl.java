package com.km.service;

import com.km.converter.IngredientDtoToEntityConverter;
import com.km.converter.IngredientEntityToDtoConverter;
import com.km.dto.IngredientDto;
import com.km.model.Ingredient;
import com.km.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientEntityToDtoConverter toDtoConverter;
    private final IngredientDtoToEntityConverter toEntityConverter;

    @Override
    public Set<IngredientDto> findAllByRecipeId(String recipeId) {
        return ingredientRepository.findAllByRecipeId(recipeId)
                .stream()
                .map(toDtoConverter::convert)
                .collect(toSet());
    }

    @Override
    public IngredientDto findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {
        return ingredientRepository.findByRecipeIdAndId(recipeId, ingredientId)
                .map(toDtoConverter::convert)
                .orElse(null);
    }

    @Override
    public IngredientDto save(IngredientDto dto) {
        Ingredient savedEntity = ingredientRepository.save(toEntityConverter.convert(dto));
        return toDtoConverter.convert(savedEntity);
    }

    @Override
    public void deleteById(String ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }
}
