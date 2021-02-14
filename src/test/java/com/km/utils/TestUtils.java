package com.km.utils;

import com.km.dto.RecipeDto;
import com.km.exception.NotFoundException;
import com.km.model.Recipe;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class TestUtils {

    public static String buildUrl(String... params) {
        return Arrays.stream(params)
                .map(param -> "/" + param)
                .collect(Collectors.joining());
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> List<T> getRecipes(Class<T> clazz) {
        final long amount = 5L;
        final T instance = clazz.newInstance();
        List<T> recipes = new ArrayList<>();
        LongStream.rangeClosed(1, amount)
                .forEach(value -> {
                            if (instance instanceof Recipe) {
                                recipes.add((T) getRecipeEntity(String.valueOf(value)));
                            }

                            if (instance instanceof RecipeDto) {
                                recipes.add((T) getRecipeDto(String.valueOf(value)));
                            }
                        }
                );
        return recipes;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T getById(Class<T> clazz, String id) {
        final T instance = clazz.newInstance();
        final Supplier<NotFoundException> notFoundSupplier = () -> new NotFoundException("Recipe not found");
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

    private static Recipe getRecipeEntity(String id) {
        return Recipe.builder()
                .id(id)
                .description(String.format("Recipe entity %s", id))
                .build();
    }

    private static RecipeDto getRecipeDto(String id) {
        return RecipeDto.builder()
                .id(id)
                .description(String.format("Recipe dto %s", id))
                .build();
    }
}
