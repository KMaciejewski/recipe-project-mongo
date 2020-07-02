package com.km.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "recipes")
@EqualsAndHashCode(exclude = "recipes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private String id;
    private String description;
    private Set<Recipe> recipes = new HashSet<>();
}
