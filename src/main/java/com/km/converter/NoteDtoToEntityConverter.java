package com.km.converter;

import com.km.dto.NoteDto;
import com.km.model.Note;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoteDtoToEntityConverter implements Converter<NoteDto, Note> {

    @Override
    public Note convert(NoteDto source) {
        return Note.builder()
                .id(source.getId())
                .recipeNote(source.getRecipeNote())
                .build();
    }
}
