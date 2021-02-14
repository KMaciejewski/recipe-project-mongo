package com.km.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "recipes")
@EqualsAndHashCode(exclude = "recipes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Category {

    @Id
    private String id;
    private String description;

    @Builder.Default
    private Set<Recipe> recipes = new HashSet<>();
}
