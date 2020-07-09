package com.km.converter;

import com.km.converter.NoteEntityToDtoConverter;
import com.km.dto.NoteDto;
import com.km.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteEntityToDtoConverterTest {

    private NoteEntityToDtoConverter converter;

    @BeforeEach
    void setUp() {
        converter = new NoteEntityToDtoConverter();
    }

    @Test
    void convert() {
        Note entity = Note.builder()
                .id("1")
                .recipeNote("Recipe note")
                .build();

        NoteDto result = converter.convert(entity);

        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getRecipeNote(), result.getRecipeNote());
    }
}