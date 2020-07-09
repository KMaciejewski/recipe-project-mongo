package com.km.controller;

import com.km.dto.RecipeDto;
import com.km.service.ImageService;
import com.km.utils.TestUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ImageControllerTest {

    private static final String ONE = "1";

    @Mock
    private ImageService imageService;

    private ImageController imageController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        imageController = new ImageController(imageService);

        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @SneakyThrows
    @Test
    void getImageForm() {
        when(imageService.findById(anyString())).thenReturn(RecipeDto.builder().id(ONE).build());

        mockMvc.perform(MockMvcRequestBuilders.get(TestUtils.buildUrl("recipe/", ONE, "/image-upload")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/image-upload-form"));

        Mockito.verify(imageService).findById(ONE);
    }

    @SneakyThrows
    @Test
    void saveImage() {
        final MockMultipartFile image = new MockMultipartFile("image", "someFile.txt",
                "text/plain", "Recipe Application".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart(TestUtils.buildUrl("recipe/", ONE, "/image")).file(image))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipes/" + ONE))
                .andExpect(view().name("redirect:/recipes/" + ONE));

        Mockito.verify(imageService).save(ONE, image);
    }

    @SneakyThrows
    @Test
    void getImageFromDB() {
        String text = "Fetching image from database";
        final byte[] bytes = text.getBytes();
        final InputStream inputStream = new ByteArrayInputStream(bytes);
        when(imageService.getImageInputStream(anyString())).thenReturn(inputStream);

        final MockHttpServletResponse response =
                mockMvc.perform(get(TestUtils.buildUrl("recipe/", ONE, "/image")))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();

        final byte[] responseBytes = response.getContentAsByteArray();

        Assertions.assertEquals(bytes.length, responseBytes.length);
    }
}