package com.km.service;

import com.km.converter.RecipeEntityToDtoConverter;
import com.km.model.Recipe;
import com.km.repository.reactive.RecipeReactiveRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import reactor.core.publisher.Mono;

import java.io.InputStream;

import static org.mockito.ArgumentMatchers.anyString;

class ImageServiceImplTest {

    @Mock
    private RecipeReactiveRepository recipeReactiveRepository;

    @Mock
    private RecipeEntityToDtoConverter toDtoConverter;

    private ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        imageService = new ImageServiceImpl(recipeReactiveRepository, toDtoConverter);
    }

    @Test
    void save() {
        final MockMultipartFile image = new MockMultipartFile("image", "someFile.txt",
                "text/plain", "Recipe Application".getBytes());
        Mockito.when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(Recipe.builder().id("1").build()));

        imageService.save("1", image);

        Mockito.verify(recipeReactiveRepository).findById(anyString());
    }

    @Test
    void findById() {
        Mockito.when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(Recipe.builder().build()));

        imageService.findById(anyString());

        Mockito.verify(recipeReactiveRepository).findById(anyString());
    }

    @SneakyThrows
    @Test
    void getImageInputStream() {
        Byte[] bytes = new Byte[]{'1', '2', '3', '4'};
        Mockito.when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(Recipe.builder().id("1").image(bytes).build()));

        final InputStream result = imageService.getImageInputStream(anyString()).block();

        Assertions.assertEquals(bytes.length, result.available());
    }
}