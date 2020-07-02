package com.km.unit.utils;

import com.km.dto.RecipeDto;
import com.km.model.Recipe;
import lombok.SneakyThrows;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TestUtils {

    public static String buildUrl(String... params) {
        return Arrays.stream(params)
                .map(param -> "/" + param)
                .collect(Collectors.joining());
    }

    @SneakyThrows
    public static <T> Set<T> getRecipes(Class<T> clazz) {
        final long amount = 5L;
        final T instance = clazz.newInstance();
        Set<T> recipes = new HashSet<>();
        LongStream.rangeClosed(1, amount)
                .forEach(value -> {
                            if (instance instanceof Recipe) {
                                recipes.add((T) getRecipeEntity(value));
                            }

                            if (instance instanceof RecipeDto) {
                                recipes.add((T) getRecipeDto(value));
                            }
                        }
                );
        return recipes;
    }

    @SneakyThrows
    public static <T> T getById(Class<T> clazz, Long id) {
        final T instance = clazz.newInstance();
        final Supplier<EntityNotFoundException> notFoundSupplier = () -> new EntityNotFoundException("Recipe not found");
        if (instance instanceof Recipe) {
            return (T) getRecipes(Recipe.class)
                    .stream()
                    .filter(recipe -> id.equals(recipe.getId()))
                    .findFirst()
                    .orElseThrow(notFoundSupplier);
        }

        if (instance instanceof RecipeDto) {
            return (T) getRecipes(RecipeDto.class)
                    .stream()
                    .filter(recipe -> id.equals(recipe.getId()))
                    .findFirst()
                    .orElseThrow(notFoundSupplier);
        }
        return null;
    }

    private static Recipe getRecipeEntity(Long id) {
        return Recipe.builder()
                .id(id)
                .description(String.format("Recipe entity %d", id))
                .build();
    }

    private static RecipeDto getRecipeDto(Long id) {
        return RecipeDto.builder()
                .id(id)
                .description(String.format("Recipe dto %d", id))
                .build();
    }


}
