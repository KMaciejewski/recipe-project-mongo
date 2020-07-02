package com.km.unit.service;

import com.km.converter.RecipeEntityToDtoConverter;
import com.km.model.Recipe;
import com.km.repository.RecipeRepository;
import com.km.service.ImageServiceImpl;
import lombok.SneakyThrows;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeEntityToDtoConverter toDtoConverter;

    private ImageServiceImpl imageService;

    @BeforeEach
    void setUp() {
        imageService = new ImageServiceImpl(recipeRepository, toDtoConverter);
    }

    @Test
    void save() {
        final MockMultipartFile image = new MockMultipartFile("image", "someFile.txt",
                "text/plain", "Recipe Application".getBytes());
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(Recipe.builder().id(1L).build()));

        imageService.save(1L, image);

        Mockito.verify(recipeRepository).findById(anyLong());
    }

    @Test
    void findById() {
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(Recipe.builder().build()));

        imageService.findById(anyLong());

        Mockito.verify(recipeRepository).findById(anyLong());
        Mockito.verify(toDtoConverter).convert(any());
    }

    @SneakyThrows
    @Test
    void getImageInputStream() {
        Byte[] bytes = new Byte[]{'1', '2', '3', '4'};
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(Recipe.builder().id(1L).image(bytes).build()));

        final InputStream result = imageService.getImageInputStream(anyLong());

        Assertions.assertEquals(bytes.length, result.available());
    }
}