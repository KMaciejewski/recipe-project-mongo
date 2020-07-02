package com.km.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private String id;
    private String description;
    private BigDecimal amount;
    private String recipeId;
    private UnitOfMeasureDto unitOfMeasure;
}
