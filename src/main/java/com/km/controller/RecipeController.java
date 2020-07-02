package com.km.controller;

import com.km.dto.RecipeDto;
import com.km.exception.NotFoundException;
import com.km.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("recipes")
@RequiredArgsConstructor
public class RecipeController {

    private static final String RECIPE_FORM_URL = "recipes/form";

    private final RecipeService recipeService;

    @GetMapping("{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipes/show";
    }

    @GetMapping("new")
    public String create(Model model) {
        model.addAttribute("recipe", new RecipeDto());
        return "recipes/form";
    }

    @GetMapping("{id}/update")
    public String update(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return RECIPE_FORM_URL;
    }

    @PostMapping
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeDto recipe, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> log.debug(error.toString()));
            return RECIPE_FORM_URL;
        }

        final RecipeDto savedDto = recipeService.save(recipe);
        return "redirect:/recipes/" + savedDto.getId();
    }

    /*
     GetMapping because Thymeleaf is an HTML template engine.
     HTML does not support PUT or DELETE http method.
     */
    @GetMapping("{id}/delete")
    public String delete(@PathVariable String id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView mav = new ModelAndView();
        mav.setViewName("404error");
        mav.addObject("exception", exception);
        return mav;
    }
}
