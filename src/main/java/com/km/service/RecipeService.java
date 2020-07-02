package com.km.service;

import com.km.dto.RecipeDto;

import java.util.Set;

public interface RecipeService {

    Set<RecipeDto> findAll();

    RecipeDto findById(String id);

    RecipeDto save(RecipeDto dto);

    void deleteById(String id);
}
