package com.km.unit.controller;

import com.km.controller.ControllerExceptionHandler;
import com.km.controller.ImageController;
import com.km.dto.RecipeDto;
import com.km.service.ImageService;
import com.km.unit.utils.TestUtils;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {

    private static Long ONE = 1L;

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
        when(imageService.findById(anyLong())).thenReturn(RecipeDto.builder().id(ONE).build());

        mockMvc.perform(get(TestUtils.buildUrl("recipe/", ONE.toString(), "/image-upload")))
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

        mockMvc.perform(MockMvcRequestBuilders.multipart(TestUtils.buildUrl("recipe/", ONE.toString(), "/image")).file(image))
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
        when(imageService.getImageInputStream(anyLong())).thenReturn(inputStream);

        final MockHttpServletResponse response =
                mockMvc.perform(get(TestUtils.buildUrl("recipe/", ONE.toString(), "/image")))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();

        final byte[] responseBytes = response.getContentAsByteArray();

        Assertions.assertEquals(bytes.length, responseBytes.length);
    }
}