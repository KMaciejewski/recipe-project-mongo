package com.km.unit.converter;

import com.km.converter.NoteDtoToEntityConverter;
import com.km.dto.NoteDto;
import com.km.model.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoteDtoToEntityConverterTest {

    private NoteDtoToEntityConverter converter;

    @BeforeEach
    void setUp() {
        converter = new NoteDtoToEntityConverter();
    }

    @Test
    void convert() {
        NoteDto dto = NoteDto.builder()
                .id("1")
                .recipeNote("Recipe note")
                .build();

        Note result = converter.convert(dto);

        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getRecipeNote(), result.getRecipeNote());
    }
}