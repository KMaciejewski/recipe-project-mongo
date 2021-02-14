package com.km.controller;

import com.km.dto.IngredientDto;
import com.km.service.IngredientService;
import com.km.service.UnitOfMeasureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static java.lang.String.format;

@Controller
@RequiredArgsConstructor
public class IngredientController {

    private static final String BASE_URL = "recipe/{recipeId}/ingredients";

    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @GetMapping(BASE_URL)
    public String findAllByRecipeId(@PathVariable String recipeId, Model model) {
        model.addAttribute("ingredients", ingredientService.findAllByRecipeId(recipeId).collectList().block());
        model.addAttribute("recipeId", recipeId);
        return "ingredients/list";
    }

    @GetMapping(BASE_URL + "/{ingredientId}")
    public String findAllByRecipeId(@PathVariable String recipeId,
                                    @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
        return "ingredients/show";
    }

    @GetMapping(BASE_URL + "/{ingredientId}/update")
    public String update(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(recipeId, ingredientId).block());
        model.addAttribute("uomList", unitOfMeasureService.findAll().collectList().block());
        return "ingredients/form";
    }

    @PostMapping(BASE_URL)
    public String saveOrUpdate(@ModelAttribute IngredientDto dto) {
        IngredientDto savedDto = ingredientService.save(dto).block();
        return format("redirect:/recipe/%s/ingredients/%s", dto.getRecipeId(), savedDto.getId());
    }

    @GetMapping(BASE_URL + "/new")
    public String create(@PathVariable String recipeId, Model model) {
        final IngredientDto dtoWithRecipeId = IngredientDto.builder().recipeId(recipeId).build();
        model.addAttribute("ingredient", dtoWithRecipeId);
        model.addAttribute("uomList", unitOfMeasureService.findAll().collectList().block());
        return "ingredients/form";
    }

    @GetMapping(BASE_URL + "/{ingredientId}/delete")
    public String delete(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(ingredientId);
        return format("redirect:/recipe/%s/ingredients", recipeId);
    }
}
