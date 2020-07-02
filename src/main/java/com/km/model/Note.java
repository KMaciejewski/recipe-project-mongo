package com.km.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "recipe")
@EqualsAndHashCode(exclude = "recipe")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    private String id;
    private Recipe recipe;
    private String recipeNote;
}
