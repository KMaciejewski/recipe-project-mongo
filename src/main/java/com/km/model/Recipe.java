package com.km.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"note", "ingredients", "categories"})
@EqualsAndHashCode(exclude = {"note", "ingredients", "categories"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String direction;
    private Difficulty difficulty;
    private Byte[] image;
    private Note note;

    @Builder.Default
    private Set<Ingredient> ingredients = new HashSet<>();

    @DBRef
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    public void setNote(Note note) {
        if (note != null) {
            this.note = note;
        }
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }
}
