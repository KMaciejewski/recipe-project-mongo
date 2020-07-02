package com.km.converter;

import com.km.dto.NoteDto;
import com.km.model.Note;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoteEntityToDtoConverter implements Converter<Note, NoteDto> {

    @Override
    public NoteDto convert(Note source) {
        return NoteDto.builder()
                .id(source.getId())
                .recipeNote(source.getRecipeNote())
                .build();
    }
}
